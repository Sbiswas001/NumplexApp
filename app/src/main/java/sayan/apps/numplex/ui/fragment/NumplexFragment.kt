package sayan.apps.numplex.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import sayan.apps.numplex.R
import sayan.apps.numplex.decimalToBin
import sayan.apps.numplex.decimalToHex
import sayan.apps.numplex.decimalToOct
import sayan.apps.numplex.digitCount
import sayan.apps.numplex.digitSum
import sayan.apps.numplex.factors
import sayan.apps.numplex.isAbundantNumber
import sayan.apps.numplex.isArmstrongNumber
import sayan.apps.numplex.isAutomorphicNumber
import sayan.apps.numplex.isCircularPrimeNumber
import sayan.apps.numplex.isDisariumNumber
import sayan.apps.numplex.isDuckNumber
import sayan.apps.numplex.isEmirpNumber
import sayan.apps.numplex.isEven
import sayan.apps.numplex.isFermatNumber
import sayan.apps.numplex.isFibonacciNumber
import sayan.apps.numplex.isHappyNumber
import sayan.apps.numplex.isKaprekarNumber
import sayan.apps.numplex.isLucasNumber
import sayan.apps.numplex.isNeonNumber
import sayan.apps.numplex.isNivenNumber
import sayan.apps.numplex.isPalindrome
import sayan.apps.numplex.isPrimeNumber
import sayan.apps.numplex.isPronicNumber
import sayan.apps.numplex.isSmithNumber
import sayan.apps.numplex.isSpecialNumber
import sayan.apps.numplex.isSpyNumber
import sayan.apps.numplex.isTechNumber
import sayan.apps.numplex.isUglyNumber
import sayan.apps.numplex.primeFactorization
import sayan.apps.numplex.primeFactors
import sayan.apps.numplex.reverse
import kotlin.math.pow
import kotlin.random.Random

class NumplexFragment : Fragment(), View.OnClickListener {

    private lateinit var input: EditText
    private lateinit var showProperties: Button
    private lateinit var clear: Button
    private lateinit var random: Button
    private lateinit var display: TextView
    private var num = 1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numplex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input = view.findViewById(R.id.input_number)
        showProperties = view.findViewById(R.id.show_properties)
        clear = view.findViewById(R.id.clear_properties)
        random = view.findViewById(R.id.random_properties)
        display = view.findViewById(R.id.answer)

        input.setOnClickListener(this)
        showProperties.setOnClickListener(this)
        clear.setOnClickListener(this)
        random.setOnClickListener(this)
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(requireContext())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
        when (v?.id) {
            R.id.show_properties -> {
                if (!isLong(input.text.toString())) {
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

            R.id.clear_properties -> {
                input.setText(getString(R.string.empty_text))
                display.text = getString(R.string.empty_text)
                input.error = null
                showKeyboard(input)
            }

            R.id.random_properties -> {
                num = generateRandomNumber()
                input.setText(num.toString())
                hideKeyboard()
                showProperties()
            }
        }
    }

    private fun showProperties() {
        var result = ""
        result += if (isEven(num)) "• Number is Even\n" else "• Number is Odd\n"
        result += "• Number of digits is ${digitCount(num)}\n"
        result += "• Sum of digits is ${digitSum(num)}\n"
        result += "• Reverse of number is ${reverse(num)}\n"
        result += "• Prime factorization of number is ${primeFactorization(num)}\n"
        result += "• Prime factors are ${primeFactors(num).toSet().joinToString(separator = ", ")}\n"
        result += "• Number of prime factors is ${primeFactors(num).toSet().size}\n"
        result += "• Sum of prime factors is ${primeFactors(num).toSet().sum()}\n"
        result += "• Factors of $num are ${factors(num).toSet().joinToString(separator = ", ")}\n"
        result += "• Number of factors is ${factors(num).toSet().size}\n"
        result += "• Sum of factors is ${factors(num).toSet().sum()}\n"
        result += "• $num is ${if (!isPrimeNumber(num)) "a Composite" else "not a Composite"} number\n"
        result += "• Binary representation is ${decimalToBin(num)}\n"
        result += "• Octal representation is ${decimalToOct(num)}\n"
        result += "• Hexadecimal representation is ${decimalToHex(num)}\n"
        result += "• $num is ${if (isPalindrome(num)) "a Palindrome" else "not a Palindrome"} number\n"
        result += "• $num is ${if (isNivenNumber(num)) "a Niven" else "not a Niven"} number\n"
        result += "• $num is ${if (isEmirpNumber(num)) "an Emirp" else "not an Emirp"} Number\n"
        result += "• $num is ${
            if (isAbundantNumber(num) <= 0) "not an Abundant number " else "an Abundant number with Abundance ${
                isAbundantNumber(
                    num
                )
            }"
        } \n"
        result += "• $num is ${if (isTechNumber(num)) "a Tech" else "not a Tech"} number\n"
        result += "• $num is ${if (isDisariumNumber(num)) "a Disarium" else "not a Disarium"} number\n"
        result += "• $num is ${if (isPronicNumber(num)) "a Pronic" else "not a Pronic"} number\n"
        result += "• $num is ${if (isAutomorphicNumber(num)) "an Automorphic" else "not an Automorphic"} number\n"
        result += "• $num is ${if (isKaprekarNumber(num)) "a Kaprekar" else "not a Kaprekar"} number\n"
        result += "• $num is ${if (isSpecialNumber(num)) "a Special" else "not a Special"} number\n"
        result += "• $num is ${if (isLucasNumber(num)) "a Lucas" else "not a Lucas"} number\n"
        result += "• $num is ${if (isSmithNumber(num)) "a Smith" else "not a Smith"} number\n"
        result += "• $num is ${if (isArmstrongNumber(num)) "an Armstrong" else "not an Armstrong"} number.\n"
        result += "• $num is ${if (isFibonacciNumber(num)) "a Fibonacci" else "not a Fibonacci"} number\n"
        result += "• $num is ${if (isCircularPrimeNumber(num)) "a Circular Prime" else "not a Circular Prime"} number\n"
        result += "• $num is ${if (isPalindrome(num) && isPrimeNumber(num)) "a Prime Palindrome" else "not a Prime Palindrome"} number\n"
        result += "• $num is ${if (isFermatNumber(num)) "a Fermat" else "not a Fermat"} number\n"
        result += "• $num is ${if (isUglyNumber(num)) "an Ugly" else "not an Ugly"} number\n"
        result += "• $num is ${if (isNeonNumber(num)) "a Neon" else "not a Neon"} number\n"
        result += "• $num is ${if (isSpyNumber(num)) "a Spy" else "not a Spy"} number\n"
        result += "• $num is ${if (isHappyNumber(num)) "a Happy" else "not a Happy"} number\n"
        result += "• $num is ${if (isDuckNumber(num)) "a Duck" else "not a Duck"} number\n"
        display.text = result
    }
}
