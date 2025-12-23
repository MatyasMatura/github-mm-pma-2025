package com.example.christmasappadventcalendar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.christmasappadventcalendar.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day, container, false)

        // Calculate days until Christmas Eve
        val countdownText = view.findViewById<TextView>(R.id.countdownText)
        updateCountdown(countdownText)

        return view
    }

    private fun updateCountdown(textView: TextView) {
        val today = LocalDate.now()
        val christmasEve = LocalDate.of(today.year, 12, 24)

        val daysUntil = ChronoUnit.DAYS.between(today, christmasEve)

        when {
            daysUntil > 0 -> {
                textView.text = daysUntil.toString()
            }
            daysUntil == 0L -> {
                textView.text = getString(R.string.about_countdown_christmas_eve)
                textView.textSize = 32f
            }
            else -> {
                textView.text = getString(R.string.about_countdown_after_christmas)
                textView.textSize = 28f
            }
        }
    }
}
