package com.example.cvikoapp05toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp05toastsnackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSnackbar.setOnClickListener {

            Snackbar.make(binding.root, "This is a Snackbar", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor("#FFBB86FC"))
                .setTextColor(Color.BLUE)
                .setDuration(7000)
                .setActionTextColor(Color.WHITE)
                .setAction("Zavřít") {
                    Toast.makeText(this, "Snackbar zavřen", Toast.LENGTH_LONG)
                        .show()
                }
                .show()
        }

        binding.btnToast.setOnClickListener {

            Toast.makeText(this, "This is a Toast", Toast.LENGTH_LONG)
                .show()
        }
    }
}