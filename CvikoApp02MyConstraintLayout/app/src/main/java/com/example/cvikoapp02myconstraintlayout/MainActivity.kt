package com.example.cvikoapp02myconstraintlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        val etName = findViewById<EditText>(R.id.etTeamName)
        val etLeague = findViewById<EditText>(R.id.etTeamLeague)
        val etCountry = findViewById<EditText>(R.id.etTeamCountry)
        val etCity = findViewById<EditText>(R.id.etTeamCity)
        val tvInfo = findViewById<TextView>(R.id.tvInfo)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Nastavení obsluhy tlačítka "Odeslat"
        btnSend.setOnClickListener {
            val name = etName.text.toString()
            val country = etCountry.text.toString()
            val league = etLeague.text.toString()
            val city = etCity.text.toString()
            val info = "Jméno: $name\nZemě: $country\nLiga: $league\nMěsto: $city"
            tvInfo.text = info
        }

        // Nastavení obsluhy tlačítka "Smazat"
        btnDelete.setOnClickListener {
            etName.text.clear()
            etCountry.text.clear()
            etLeague.text.clear()
            etCity.text.clear()
            tvInfo.text = ""
        }
    }
}