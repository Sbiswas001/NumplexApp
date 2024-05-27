package sayan.apps.numplex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.MenuItem

import android.os.Handler
import android.os.Looper

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

import kotlin.random.Random
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import sayan.apps.numplex.databinding.ActivityMainBinding
import sayan.apps.numplex.ui.ClassificationFragment
import sayan.apps.numplex.ui.NumplexFragment


class MainActivity : AppCompatActivity() , /*View.OnClickListener ,*/ NavigationView.OnNavigationItemSelectedListener {

    /*private lateinit var input: EditText
    private lateinit var showProperties: Button
    private lateinit var clear: Button
    private lateinit var random: Button
    private lateinit var display: TextView

    private var num = 1*/
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolBar: Toolbar
    private var num = 1L
    private var keepSplashScreenOn = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashScreenOn = false
        }, 1600)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*input = findViewById(R.id.input_number)
        showProperties = findViewById(R.id.show_properties)
        clear = findViewById(R.id.clear_properties)
        random = findViewById(R.id.random_properties)
        display = findViewById(R.id.answer)

        input.setOnClickListener(this)
        showProperties.setOnClickListener(this)
        clear.setOnClickListener(this)
        random.setOnClickListener(this)*/



        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toolBar = findViewById(R.id.toolbar)

        setSupportActionBar(toolBar)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar , R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val numplexFragment = NumplexFragment()
        loadFragment(numplexFragment)

        /*val intent = Intent(this, NumplexActivity::class.java)

        startActivity(intent)*/


        navView.setNavigationItemSelectedListener(this)

        drawerLayout.closeDrawer(GravityCompat.START)


    }
/*
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun generateRandomNumber(): Long {
        val randomLength = Random.nextInt(1, 11)
        return Random.nextLong(1L, 10.0.pow(randomLength.toDouble()).toLong())
    }

    private fun isLong(s: String): Boolean {
        return try {
            s.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.show_properties ->{

                if(!isLong(input.text.toString())) {
                    input.error = "Max 10-digit supported"
                    return
                }
                val inputNumber = input.text.toString().toLong()
                if (inputNumber <= 0) {
                    input.error = "Number should be > 0"
                    return
                }
                if (inputNumber > 9999999999) {
                    input.error = "Max 10-digit supported"
                    return
                }
                num = inputNumber
                hideKeyboard()
                showProperties()
            }
            R.id.clear_properties ->{
                input.setText("")
                display.text = ""
                input.error = null
                showKeyboard(input)
            }
            R.id.random_properties ->{
                num = generateRandomNumber()
                input.setText(num.toString())
                hideKeyboard()
                showProperties()
            }
        }
    }

    private fun showProperties(){
        var result = ""
        result+= if (isEven(num)) "Number is Even\n" else "Number is Odd\n"
        result+= "Number of digits is ${digitCount(num)}\n"
        result+= "Sum of digits is ${digitSum(num)}\n"
        result+= "Reverse of number is ${reverse(num)}\n"
        result+= "Prime factorization is ${primeFactorization(num)}\n"
        result+= "Prime factors are ${primeFactors(num).toSet().joinToString(separator = ", ")}\n"
        result+= "Number of prime factors is ${primeFactors(num).toSet().size}\n"
        result+= "Sum of prime factors is ${primeFactors(num).toSet().sum()}\n"
        result += "Factors of $num are ${factors(num).toSet().joinToString(separator = ", ")}\n"
        result += "Number of factors is ${factors(num).toSet().size}\n"
        result += "Sum of factors is ${factors(num).toSet().sum()}\n"
        result += "$num is ${if(!isPrimeNumber(num)) "a Composite" else "not a Composite"} number\n"
        result += "Binary representation is ${decimalToBin(num)}\n"
        result += "Octal representation is ${decimalToOct(num)}\n"
        result += "Hexadecimal representation is ${decimalToHex(num)}\n"
        result += "$num is ${if(isPalindrome(num)) "a Palindrome" else "not a Palindrome"} number\n"
        result += "$num is ${if(isNivenNumber(num)) "a Niven"  else "not a Niven"} number\n"
        result += "$num is ${if(isEmirpNumber(num)) "an Emirp"  else "not an Emirp"} Number\n"
        result += "$num is ${if(isAbundantNumber(num)<=0) "not an Abundant number " else "an Abundant number with Abundance ${isAbundantNumber(num)}"} \n"
        result += "$num is ${if(isTechNumber(num)) "a Tech"  else "not a Tech"} number\n"
        result += "$num is ${if(isDisariumNumber(num)) "a Disarium"  else "not a Disarium"} number\n"
        result += "$num is ${if(isPronicNumber(num)) "a Pronic"  else "not a Pronic"} number\n"
        result += "$num is ${if(isAutomorphicNumber(num)) "an Automorphic"  else "not an Automorphic"} number\n"
        result += "$num is ${if(isKaprekarNumber(num)) "a Kaprekar"  else "not a Kaprekar"} number\n"
        result += "$num is ${if(isSpecialNumber(num)) "a Special"  else "not a Special"} number\n"
        result += "$num is ${if(isLucasNumber(num)) "a Lucas"  else "not a Lucas"} number\n"
        result += "$num is ${if(isSmithNumber(num)) "a Smith"  else "not a Smith"} number\n"
        result += "$num is ${if(isArmstrongNumber(num)) "an Armstrong"  else "not an Armstrong"} number.\n"
        result += "$num is ${if(isFibonacciNumber(num)) "a Fibonacci"  else "not a Fibonacci"} number\n"
        result += "$num is ${if(isCircularPrimeNumber(num)) "a Circular Prime"  else "not a Circular Prime"} number\n"
        result += "$num is ${if(isPalindrome(num) && isPrimeNumber(num)) "a Prime Palindrome" else "not a Prime Palindrome"} number\n"
        result += "$num is ${if(isFermatNumber(num)) "a Fermat" else "not a Fermat"} number\n"
        result += "$num is ${if(isUglyNumber(num)) "an Ugly" else "not an Ugly"} number\n"
        result += "$num is ${if(isNeonNumber(num)) "a Neon" else "not a Neon"} number\n"
        result += "$num is ${if(isSpyNumber(num)) "a Spy" else "not a Spy"} number\n"
        result += "$num is ${if(isHappyNumber(num)) "a Happy" else "not a Happy"} number\n"
        result += "$num is ${if(isDuckNumber(num)) "a Duck" else "not a Duck"} number\n"
        display.text = result
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_numplex -> {
                val numplexFragment = NumplexFragment()
                loadFragment(numplexFragment)
                Toast.makeText(this, "You are in Numplex", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_numbers -> {
                val classificationFragment = ClassificationFragment()
                loadFragment(classificationFragment)
                Toast.makeText(this, "You are in Classifications", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "You are in LogOut", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_feedback -> {
                Toast.makeText(this, "You are in Feedback", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                Toast.makeText(this, "You are in Update", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override public fun onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container , fragment).commit()
    }

}


