package sayan.apps.numplex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.coroutines.coroutineContext

class ClassificationsActivity : AppCompatActivity() ,View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_classifications)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.classificationPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val primeNum: Button = findViewById(R.id.primeNum)
        val compositeNum: Button = findViewById(R.id.compositeNum)
        val nivenNum: Button = findViewById(R.id.nivenNum)
        val emirpNum: Button = findViewById(R.id.emirpNum)
        val abundantNum: Button = findViewById(R.id.abundantNum)
        val techNum: Button = findViewById(R.id.techNum)
        val disariumNum: Button = findViewById(R.id.disariumNum)
        val pronicNum: Button = findViewById(R.id.pronicNum)
        val automorphicNum: Button = findViewById(R.id.automorphicNum)
        val kaprekarNum: Button = findViewById(R.id.kaprekarNum)
        val specialNum: Button = findViewById(R.id.specialNum)
        val lucasNum: Button = findViewById(R.id.lucasNum)
        val smithNum: Button = findViewById(R.id.smithNum)
        val armstrongNum: Button = findViewById(R.id.armstrongNum)
        val fermatNum: Button = findViewById(R.id.fermatNum)
        val uglyNum: Button = findViewById(R.id.uglyNum)
        val neonNum: Button = findViewById(R.id.neonNum)
        val spyNum: Button = findViewById(R.id.spyNum)
        val happyNum: Button = findViewById(R.id.happyNum)
        val duckNum: Button = findViewById(R.id.duckNum)


        primeNum.setOnClickListener(this)
        primeNum.setOnClickListener{
            showDefinition()

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.primeNum ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Name")
                    .setMessage("Definition")
                    .setPositiveButton("Ok") { dialog, which ->
                        dialog.dismiss()
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        }
    }

    private fun showDefinition() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Name")
            .setMessage("Definition")
            .setPositiveButton("Ok") { dialog, which ->
                dialog.dismiss()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}