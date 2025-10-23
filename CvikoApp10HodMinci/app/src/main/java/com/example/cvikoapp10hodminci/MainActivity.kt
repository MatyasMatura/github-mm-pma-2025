package com.example.cvikoapp10hodminci

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.cvikoapp10hodminci.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var correctGuesses: Int = 0
    private var totalGuesses: Int = 0
    private var isFlipping = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPanna.setOnClickListener {
            if (!isFlipping) handleGuess("Panna")
        }
        binding.btnOrel.setOnClickListener {
            if (!isFlipping) handleGuess("Orel")
        }
    }

    private fun handleGuess(userGuess: String) {
        isFlipping = true
        binding.tvResult.text = ""

        // Načtení animace
        val flipAnimation = AnimationUtils.loadAnimation(this, R.anim.coin_flip)

        // Spuštění animace
        binding.ivCoin.startAnimation(flipAnimation)

        // Náhodný výsledek
        val coinResult = if ((0..1).random() == 0) "Panna" else "Orel"

        // Po dokončení animace zobrazit výsledek
        Handler(Looper.getMainLooper()).postDelayed({
            // Změna obrázku podle výsledku
            if (coinResult == "Panna") {
                binding.ivCoin.setImageResource(R.drawable.coin_panna)
            } else {
                binding.ivCoin.setImageResource(R.drawable.coin_orel)
            }

            // Zobrazení výsledku
            totalGuesses++
            if (userGuess == coinResult) {
                correctGuesses++
                binding.tvResult.text = getString(R.string.result_correct, coinResult, correctGuesses, totalGuesses)
            } else {
                binding.tvResult.text = getString(R.string.result_wrong, coinResult, correctGuesses, totalGuesses)
            }

            isFlipping = false
        }, 1000) // 1 sekunda - doba trvání animace
    }
}