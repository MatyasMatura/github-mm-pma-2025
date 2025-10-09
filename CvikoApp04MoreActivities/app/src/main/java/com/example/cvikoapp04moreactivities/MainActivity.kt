package com.example.cvikoapp04moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvikoapp04moreactivities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSecondActivity = binding.btnSecondActivity
        val etNickname = binding.etNickname
        val etCity = binding.etCity

        btnSecondActivity.setOnClickListener {
            val nickname = etNickname.text.toString()
            val city = etCity.text.toString()
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            intent.putExtra("CITY_NAME", city)
            startActivity(intent)
        }
    }
}