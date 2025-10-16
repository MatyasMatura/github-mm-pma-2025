package com.example.cvikoapp08realsharedpreferences

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp08realsharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Funkcionalita pro ukládání dat
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString().trim()

            if (ageString.isBlank()){
                Toast.makeText(this, "Musíš vyplnit věk...", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageString.toInt()
                val isAdult = binding.cbAdult.isChecked
                if (age < 18 && isAdult || (age >= 18 && !isAdult)) {
                    Toast.makeText(this, "Nesedí ti věk a zatržení checkboxu...", Toast.LENGTH_SHORT).show()
                } else {
                    editor.putString("name", name)
                    editor.putString("age", age.toString())
                    editor.putBoolean("isAdult", isAdult)
                    editor.apply()
                    Toast.makeText(this, "Data uložena", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLoad.setOnClickListener {
            val name = sharedPreferences.getString("name", "")
            val age = sharedPreferences.getString("age", "")
            val isAdult = sharedPreferences.getBoolean("isAdult", false)
            binding.etName.setText(name)
            binding.etAge.setText(age)
            binding.cbAdult.isChecked = isAdult
            val loadedText = "Jméno: $name\nVěk: $age\nDospělý: ${if (isAdult) "Ano" else "Ne"}"
            binding.tvLoadedData.text = loadedText
            Toast.makeText(this, "Data načtena", Toast.LENGTH_SHORT).show()
        }
    }
}