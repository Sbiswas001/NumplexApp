package sayan.apps.numplex

import android.content.Intent
import android.net.Uri
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
            R.id.primeNum -> showDefinition(getString(R.string.prime_number), getString(R.string.prime_number_definition), getString(R.string.prime_number_url))
            R.id.compositeNum -> showDefinition(getString(R.string.composite_number), getString(R.string.composite_number_definition), getString(R.string.composite_number_url))
            R.id.nivenNum -> showDefinition(getString(R.string.niven_number), getString(R.string.niven_number_definition), getString(R.string.niven_number_url))
            R.id.emirpNum -> showDefinition(getString(R.string.emirp_number), getString(R.string.emirp_number_definition), getString(R.string.emirp_number_url))
            R.id.abundantNum -> showDefinition(getString(R.string.abundant_number), getString(R.string.abundant_number_definition), getString(R.string.abundant_number_url))
            R.id.techNum -> showDefinition(getString(R.string.tech_number), getString(R.string.tech_number_definition), getString(R.string.tech_number_url))
            R.id.disariumNum -> showDefinition(getString(R.string.disarium_number), getString(R.string.disarium_number_definition), getString(R.string.disarium_number_url))
            R.id.pronicNum -> showDefinition(getString(R.string.pronic_number), getString(R.string.pronic_number_definition), getString(R.string.pronic_number_url))
            R.id.automorphicNum -> showDefinition(getString(R.string.automorphic_number), getString(R.string.automorphic_number_definition), getString(R.string.automorphic_number_url))
            R.id.kaprekarNum -> showDefinition(getString(R.string.kaprekar_number), getString(R.string.kaprekar_number_definition), getString(R.string.kaprekar_number_url))
            R.id.specialNum -> showDefinition(getString(R.string.special_number), getString(R.string.special_number_definition), getString(R.string.special_number_url))
            R.id.lucasNum -> showDefinition(getString(R.string.lucas_number), getString(R.string.lucas_number_definition), getString(R.string.lucas_number_url))
            R.id.smithNum -> showDefinition(getString(R.string.smith_number), getString(R.string.smith_number_definition), getString(R.string.smith_number_url))
            R.id.armstrongNum -> showDefinition(getString(R.string.armstrong_number), getString(R.string.armstrong_number_definition), getString(R.string.armstrong_number_url))
            R.id.fermatNum -> showDefinition(getString(R.string.fermat_number), getString(R.string.fermat_number_definition), getString(R.string.fermat_number_url))
            R.id.uglyNum -> showDefinition(getString(R.string.ugly_number), getString(R.string.ugly_number_definition), getString(R.string.ugly_number_url))
            R.id.neonNum -> showDefinition(getString(R.string.neon_number), getString(R.string.neon_number_definition), getString(R.string.neon_number_url))
            R.id.spyNum -> showDefinition(getString(R.string.spy_number), getString(R.string.spy_number_definition), getString(R.string.spy_number_url))
            R.id.happyNum -> showDefinition(getString(R.string.happy_number), getString(R.string.happy_number_definition), getString(R.string.happy_number_url))
            R.id.duckNum -> showDefinition(getString(R.string.duck_number), getString(R.string.duck_number_definition), getString(R.string.duck_number_url))

        }
    }

    private fun showDefinition(title: String, message: String, url: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Visit Website") { dialog, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                dialog.dismiss()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
