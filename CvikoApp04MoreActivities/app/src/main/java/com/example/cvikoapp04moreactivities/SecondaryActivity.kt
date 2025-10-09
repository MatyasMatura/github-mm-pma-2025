package com.example.cvikoapp04moreactivities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_secondary)

        val twInfo = findViewById<TextView>(R.id.twInfo)
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivity:" + nickname

        val btnCloseActivity = findViewById<TextView>(R.id.btnCloseActivity)
        btnCloseActivity.setOnClickListener {
            finish()
        }
    }
}