package sayan.apps.numplex

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.TextView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private var keepSplashScreenOn = true
    private var isSignedIn = true
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
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

        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        drawerLayout = findViewById(R.id.drawerLayout)
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



        val account = GoogleSignIn.getLastSignedInAccount(this)
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
                    Toast.makeText(applicationContext, "Latest Version is already Installed !!", Toast.LENGTH_SHORT).show()
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

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
