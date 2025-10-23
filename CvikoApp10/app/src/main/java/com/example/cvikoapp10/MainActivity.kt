package com.example.cvikoapp10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var randomNumber: Int = 0
    private var attempts: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateRandomNumber()

        binding.btnSubmit.setOnClickListener {
            val guessText = binding.etGuess.text.toString()
            val guess = guessText.toIntOrNull()
            if (guess == null || guess !in 1..10) {
                showToast("Zadejte číslo 1-10")
                return@setOnClickListener
            }
            attempts++
            if (guess == randomNumber) {
                showToast("Správně! Uhádnuto na $attempts pokusů")
                generateRandomNumber()
                binding.etGuess.text.clear()
                attempts = 0
            } else {
                showToast("Vedle! Pokus číslo $attempts")
            }
        }
    }

    private fun generateRandomNumber() {
        randomNumber = (1..10).random()
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}