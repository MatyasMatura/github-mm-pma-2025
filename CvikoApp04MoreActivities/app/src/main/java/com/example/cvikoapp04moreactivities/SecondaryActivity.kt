package com.example.cvikoapp04moreactivities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cvikoapp04moreactivities.databinding.ActivitySecondaryBinding

class SecondaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nickname = intent.getStringExtra("NICK_NAME")
        val city = intent.getStringExtra("CITY_NAME")
        binding.twInfo.text = "Data z první aktivity: Přezdívka: $nickname, Město: $city"

        binding.btnThirdActivity.setOnClickListener {
            val favoriteColor = binding.etFavoriteColor.text.toString()
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            intent.putExtra("CITY_NAME", city)
            intent.putExtra("FAVORITE_COLOR", favoriteColor)
            startActivity(intent)
        }

        binding.btnCloseActivity.setOnClickListener {
            finish()
        }
    }
}