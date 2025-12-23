package com.example.christmasappadventcalendar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.christmasappadventcalendar.R
import com.example.christmasappadventcalendar.AdventCalendarRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment() {

    private lateinit var repository: AdventCalendarRepository
    private val dayViews = mutableListOf<TextView>()

    private val christmasEmojis = listOf(
        "🎅", "🤶", "🎄", "⛄", "🎁", "🔔", "⭐", "❄️",
        "🕯️", "🦌", "🎿", "⛷️", "🏂", "🧦", "🍪", "🥛",
        "🎵", "🎶", "🌟", "✨", "🎀", "🎊", "🎉", "🧸"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        repository = AdventCalendarRepository(requireContext())

        // Collect all day views
        for (i in 1..24) {
            val dayView = view.findViewById<TextView>(
                resources.getIdentifier("day$i", "id", requireContext().packageName)
            )
            dayViews.add(dayView)
            setupDayView(dayView, i)
        }

        return view
    }

    private fun setupDayView(dayView: TextView, day: Int) {
        lifecycleScope.launch {
            val isOpened = repository.isDayOpened(day).first()
            val emoji = repository.getDayEmoji(day).first()
            val calendar = Calendar.getInstance()
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val currentMonth = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based

            // Check if day is unlocked (it's December and today is >= day number)
            val isUnlocked = currentMonth == 12 && currentDay >= day

            when {
                isOpened && emoji != null -> {
                    // Day was already opened, show emoji
                    dayView.text = emoji
                    dayView.setBackgroundResource(R.drawable.calendar_day_opened)
                    dayView.isEnabled = false
                }
                isUnlocked -> {
                    // Day is unlocked and can be opened
                    dayView.text = day.toString()
                    dayView.setBackgroundResource(R.drawable.calendar_day_background)
                    dayView.isEnabled = true
                    dayView.setOnClickListener {
                        openDay(dayView, day)
                    }
                }
                else -> {
                    // Day is locked
                    dayView.text = "🔒"
                    dayView.setBackgroundResource(R.drawable.calendar_day_locked)
                    dayView.isEnabled = false
                    dayView.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "This door opens on December $day!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun openDay(dayView: TextView, day: Int) {
        lifecycleScope.launch {
            // Get a random emoji
            val randomEmoji = christmasEmojis.random()

            // Save to repository
            repository.markDayAsOpened(day, randomEmoji)

            // Update UI
            dayView.text = randomEmoji
            dayView.setBackgroundResource(R.drawable.calendar_day_opened)
            dayView.isEnabled = false

            // Show a toast
            Toast.makeText(
                requireContext(),
                "Day $day: $randomEmoji Happy Holidays!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}