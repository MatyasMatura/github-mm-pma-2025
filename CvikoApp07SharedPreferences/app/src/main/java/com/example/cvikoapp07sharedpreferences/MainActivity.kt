package com.example.cvikoapp07sharedpreferences

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cvikoapp07sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFragment1.setOnClickListener {
            replaceFragment(Fragment1())
            binding.tvDeviceName.text = "Notebook"
        }

        binding.btnFragment2.setOnClickListener {
            replaceFragment(Fragment2())
            binding.tvDeviceName.text = "Desktop PC"
        }

        binding.btnFragment3.setOnClickListener {
            replaceFragment(Fragment3())
            binding.tvDeviceName.text = "Tablet"
        }

        binding.btnFragment4.setOnClickListener {
            replaceFragment(Fragment4())
            binding.tvDeviceName.text = "Telefon"
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}