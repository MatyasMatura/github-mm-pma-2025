package com.example.cvikoapp14datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cvikoapp14datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repo: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = UserPreferencesRepository(this)

        // ---------------------------------------
        // SLEDOVÁNÍ HODNOT (Flow → UI)
        // ---------------------------------------

        // 1) Dark mode
        lifecycleScope.launch {
            repo.darkModeFlow.collectLatest { value ->
                binding.textPreview.setTextColor(
                    if (value) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                )
                binding.root.setBackgroundColor(
                    if (value) android.graphics.Color.DKGRAY else android.graphics.Color.WHITE
                )
                binding.switchDarkMode.isChecked = value
            }
        }

        // 2) Username
        lifecycleScope.launch {
            repo.usernameFlow.collectLatest { value ->
                binding.textPreview.text = value.ifEmpty { "Ukázkový text" }
                binding.editUsername.setText(value)
            }
        }

        // 3) Font size
        lifecycleScope.launch {
            repo.fontSizeFlow.collectLatest { value ->
                binding.textPreview.textSize = value.toFloat()
                binding.seekFontSize.progress = value
                binding.textFontSizeValue.text = "Velikost: $value"
            }
        }


        // ---------------------------------------
        // REAKCE NA UI A ULOŽENÍ DO DATASTORE
        // ---------------------------------------

        // 1) Přepnutí dark mode
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                repo.setDarkMode(isChecked)
            }
        }

        // 2) Uložení username po kliknutí na tlačítko
        binding.buttonSaveUsername.setOnClickListener {
            val name = binding.editUsername.text.toString()
            lifecycleScope.launch {
                repo.setUsername(name)
            }
        }

        // 3) Změna velikosti fontu
        binding.seekFontSize.setOnSeekBarChangeListener(
            object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar?, value: Int, fromUser: Boolean) {
                    binding.textFontSizeValue.text = "Velikost: $value"
                }

                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
                    seekBar?.let {
                        lifecycleScope.launch {
                            repo.setFontSize(it.progress)
                        }
                    }
                }
            }
        )
    }
}