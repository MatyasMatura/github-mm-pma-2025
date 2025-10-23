package com.example.cvikoapp09imagetoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp09imagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            if (uri != null) {
                binding.ivImage.setImageURI(uri)
            }
        }

        binding.btnChangeImage.setOnClickListener {
            getContent.launch("image/*")
        }
    }
}