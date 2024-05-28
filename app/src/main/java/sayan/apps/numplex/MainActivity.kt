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

class MainActivity : AppCompatActivity() {

    private var keepSplashScreenOn = true
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashScreenOn = false
        }, 1600)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
                    Toast.makeText(applicationContext, "Coming soon!", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_feedback -> {
                    Toast.makeText(applicationContext, "Coming soon!", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_logout -> {
                    Toast.makeText(applicationContext, "Coming soon!", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
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
}
