package com.example.mypersonaldiary7.ul.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypersonaldiary7.R
import com.example.mypersonaldiary7.ul.adapters.DiaryEntriesAdapter
import com.example.mypersonaldiary7.ul.viewmodels.DiaryEntriesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiaryEntriesAdapter
    private val viewModel: DiaryEntriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Home button - go to the home screen
        val homeButton: ImageButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {

        }

        // The "Add" button - go to the add screen
        val addButton: ImageButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Calendar button - go to the calendar screen
        val calendarButton: ImageButton = findViewById(R.id.calendarButton)
        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            finish()
        }
        recyclerView = findViewById(R.id.diaryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Installing an adapter for RecyclerView
        adapter = DiaryEntriesAdapter { entry ->
// When clicking on a list item, an Intent is created to go to the editing screen
            val intent = Intent(this, EditEntryActivity::class.java).apply {
                putExtra("ENTRY_ID", entry.id)
                putExtra("ENTRY_TITLE", entry.title)
                putExtra("ENTRY_DATE", entry.createdAt.time)
                putExtra("ENTRY_IMAGE", entry.imageUri)
            }
            startActivity(intent)
        }
        // Installing the adapter in the Recycler View
        recyclerView.adapter = adapter
        // Monitoring changes in the list of records via Live Data
        viewModel.diaryEntriesList.observe(this) { entries ->
            // Passing the updated list of entries to the adapter
            adapter.submitList(entries)
        }
        //Processing of clicking on the "Add entry" button
        findViewById<ImageButton>(R.id.addButton).setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }
    }
}
