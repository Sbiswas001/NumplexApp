package sayan.apps.numplex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class ClassificationsFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.classificationPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val primeNum: Button = view.findViewById(R.id.primeNum)
        val compositeNum: Button = view.findViewById(R.id.compositeNum)
        val nivenNum: Button = view.findViewById(R.id.nivenNum)
        val emirpNum: Button = view.findViewById(R.id.emirpNum)
        val abundantNum: Button = view.findViewById(R.id.abundantNum)
        val techNum: Button = view.findViewById(R.id.techNum)
        val disariumNum: Button = view.findViewById(R.id.disariumNum)
        val pronicNum: Button = view.findViewById(R.id.pronicNum)
        val automorphicNum: Button = view.findViewById(R.id.automorphicNum)
        val kaprekarNum: Button = view.findViewById(R.id.kaprekarNum)
        val specialNum: Button = view.findViewById(R.id.specialNum)
        val lucasNum: Button = view.findViewById(R.id.lucasNum)
        val smithNum: Button = view.findViewById(R.id.smithNum)
        val armstrongNum: Button = view.findViewById(R.id.armstrongNum)
        val fermatNum: Button = view.findViewById(R.id.fermatNum)
        val uglyNum: Button = view.findViewById(R.id.uglyNum)
        val neonNum: Button = view.findViewById(R.id.neonNum)
        val spyNum: Button = view.findViewById(R.id.spyNum)
        val happyNum: Button = view.findViewById(R.id.happyNum)
        val duckNum: Button = view.findViewById(R.id.duckNum)

        primeNum.setOnClickListener(this)
        compositeNum.setOnClickListener(this)
        nivenNum.setOnClickListener(this)
        emirpNum.setOnClickListener(this)
        abundantNum.setOnClickListener(this)
        techNum.setOnClickListener(this)
        disariumNum.setOnClickListener(this)
        pronicNum.setOnClickListener(this)
        automorphicNum.setOnClickListener(this)
        kaprekarNum.setOnClickListener(this)
        specialNum.setOnClickListener(this)
        lucasNum.setOnClickListener(this)
        smithNum.setOnClickListener(this)
        armstrongNum.setOnClickListener(this)
        fermatNum.setOnClickListener(this)
        uglyNum.setOnClickListener(this)
        neonNum.setOnClickListener(this)
        spyNum.setOnClickListener(this)
        happyNum.setOnClickListener(this)
        duckNum.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.primeNum -> showDefinition(getString(R.string.prime_number), "A prime number is a natural number greater than 1 that has no positive divisors other than 1 and itself.")
            R.id.compositeNum -> showDefinition("Composite Number", "A composite number is a positive integer that has at least one positive divisor other than one or itself.")
            R.id.nivenNum -> showDefinition("Niven Number", "A Niven number is a number that is divisible by the sum of its digits.")
            // Add more cases for each button with their respective definitions
            // ...
        }
    }

    private fun showDefinition(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}
