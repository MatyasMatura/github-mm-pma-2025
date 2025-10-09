package com.example.cvikoapp04moreactivities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp04moreactivities.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteColor = intent.getStringExtra("FAVORITE_COLOR")

        binding.twInfo.text = "Data z druhé aktivity:\nOblíbená barva: $favoriteColor"

        binding.btnCloseActivity.setOnClickListener {
            finish()
        }
    }
}