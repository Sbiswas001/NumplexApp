package sayan.apps.numplex

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var input: EditText
    private lateinit var showProperties: Button
    private lateinit var clear: Button
    private lateinit var random: Button
    private lateinit var display: TextView
    private var num = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        input = findViewById(R.id.input_number)
        showProperties = findViewById(R.id.show_properties)
        clear = findViewById(R.id.clear_properties)
        random = findViewById(R.id.random_properties)
        display = findViewById(R.id.answer)

        input.setOnClickListener(this)
        showProperties.setOnClickListener(this)
        clear.setOnClickListener(this)
        random.setOnClickListener(this)
    }

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

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.show_properties ->{
                fun isInteger(s: String): Boolean {
                    return try {
                        s.toInt()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                }
                if(!isInteger(input.text.toString())) {
                    input.error = "Number should be less than 999999"
                    return
                }
                if (input.text.toString().isEmpty()) {
                    input.error = "Please enter a number"
                    return
                }
                if (input.text.toString().toInt() >999999) {
                    input.error = "Number should be less than 999999"
                    return
                }
                if (input.text.toString().toInt() <=0) {
                    input.error = "Number should be greater than 0"
                    return
                }
                num = input.text.toString().toInt()
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
                num = Random.nextInt(1, 999999)
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
    }
}