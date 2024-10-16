package sayan.apps.numplex.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialManagerCallback
import androidx.credentials.exceptions.ClearCredentialException
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import sayan.apps.numplex.R
import sayan.apps.numplex.ui.fragment.ClassificationsFragment
import sayan.apps.numplex.ui.fragment.FeedbackFragment
import sayan.apps.numplex.ui.fragment.NumplexFragment
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val credentialManager: CredentialManager = CredentialManager.create(this)
    private val mAuth = FirebaseAuth.getInstance()
    private var keepSplashScreenOn = true
    private var isSignedIn = true

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var profileImage: de.hdodenhof.circleimageview.CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }
        Handler(Looper.getMainLooper()).postDelayed({
            if (!isSignedIn)
                goToSignInActivity()
            else
                keepSplashScreenOn = false
        }, 2000)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            // Close the keyboard when the drawer starts moving
            override fun onDrawerStateChanged(newState: Int) = closeKeyboard()
        })
        val navView: NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            replaceFragment(NumplexFragment(), getString(R.string.numplex))
            navView.setCheckedItem(R.id.nav_numplex)
        }

        val headerView = navView.getHeaderView(0)
        username = headerView.findViewById(R.id.user_name)
        email = headerView.findViewById(R.id.user_email)
        profileImage = headerView.findViewById(R.id.user_image)

        val account = mAuth.currentUser
        if (account == null) {
            if (!keepSplashScreenOn)
                goToSignInActivity()
            isSignedIn = false
            return
        }
        val personName = account.displayName
        val personEmail = account.email
        val personPhoto = account.photoUrl?.toString()

        username.text = personName
        email.text = personEmail
        personPhoto?.let {
            Picasso.get()
                .load(it)
                .into(profileImage)

        }

        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.nav_numplex -> {
                    replaceFragment(NumplexFragment(), it.title.toString())
                }

                R.id.nav_classifications -> {
                    replaceFragment(ClassificationsFragment(), it.title.toString())
                }

                R.id.nav_update -> {
                    Toast.makeText(
                        applicationContext,
                        "Latest Version is already Installed !!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                R.id.nav_feedback -> {
                    replaceFragment(FeedbackFragment(), it.title.toString())
                }

                R.id.nav_logout -> {
                    signOutAndStartSignInActivity()
                }
            }
            true
        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun goToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        // Clear the credential state
        val clearCredentialsRequest = ClearCredentialStateRequest()
        credentialManager.clearCredentialStateAsync(
            clearCredentialsRequest,
            CancellationSignal(),
            Executors.newSingleThreadExecutor(),
            object :
                CredentialManagerCallback<Void?, ClearCredentialException> {
                override fun onResult(result: Void?) {
                    goToSignInActivity()
                }

                override fun onError(e: ClearCredentialException) {
                    goToSignInActivity()
                }
            }
        )
    }
}
