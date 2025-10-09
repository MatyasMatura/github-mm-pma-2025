package com.example.cvikoapp04moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnSecondActivity = findViewById<Button>(R.id.btnSecondActivity)
        val etNickname = findViewById<EditText>(R.id.etNickname)

        btnSecondActivity.setOnClickListener {
            val nickname = etNickname.text.toString()
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("NICK_NAME", nickname)
            startActivity(intent)
        }
    }
}