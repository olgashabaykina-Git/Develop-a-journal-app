package com.example.mypersonaldiary7.ul.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mypersonaldiary7.ul.viewmodels.DiaryEntriesViewModel
import com.example.mypersonaldiary7.R
import com.example.mypersonaldiary7.data.model.DiaryEntries
import java.util.Date
import android.Manifest

class AddEntryActivity : AppCompatActivity() {

    private lateinit var clipButton: ImageButton
    private lateinit var selectedImageView: ImageView
    var selectedImageUri: Uri? = null

    // Launcher for requesting permissions
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Permission to access photos is required!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        //Requesting permissions to work with files
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissionLauncher.launch(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }

        // Initializing buttons and fields
        val homeButton: ImageButton = findViewById(R.id.homeButton)
        val calendarButton: ImageButton = findViewById(R.id.calendarButton)
        val addButton: ImageButton = findViewById(R.id.addButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val entryEditText = findViewById<EditText>(R.id.entryEditText)
        clipButton = findViewById(R.id.clipButton)
        selectedImageView = findViewById(R.id.selectedImageView)

        // Transitions to other screens
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        calendarButton.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
            finish()
        }

        addButton.setOnClickListener {

        }

        // Opening a document to select an image
        clipButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            pickImageLauncher.launch(intent)
        }

        // Saving a record
        saveButton.setOnClickListener {
            val entryText = entryEditText.text.toString()

            if (entryText.isNotBlank()) {
                val viewModel = ViewModelProvider(this).get(DiaryEntriesViewModel::class.java)
                val diaryEntry = DiaryEntries(
                    title = entryText,
                    createdAt = Date(),
                    imageUri = selectedImageUri?.toString()
                )

                // Saving an entry to the database
                viewModel.addDiaryEntry(diaryEntry)

                // Displaying a notification about adding an entry
                Toast.makeText(this, "An entry has been added", Toast.LENGTH_SHORT).show()

                // Explicit switch to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // End of current activity
                finish()
            } else {
                // Displaying a notification about the need to enter text
                Toast.makeText(this, "Enter the text of the entry", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Image selection launcher
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    try {
                        //Saving access to the URI
                        contentResolver.takePersistableUriPermission(
                            selectedImageUri!!,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        selectedImageView.visibility = View.VISIBLE
                        selectedImageView.setImageURI(selectedImageUri)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error loading the image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}



