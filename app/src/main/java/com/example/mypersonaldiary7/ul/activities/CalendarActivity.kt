package com.example.mypersonaldiary7.ul.activities

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypersonaldiary7.R

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)
        val homeButton = findViewById<ImageButton>(R.id.homeButton)
        val addButton = findViewById<ImageButton>(R.id.addButton)
        val calendarView: CalendarView = findViewById(R.id.calendarView)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.calendarButton).setOnClickListener {
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Processing the date selection event
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(this, "You have chosen a date: $date", Toast.LENGTH_SHORT).show()
        }
    }
}

