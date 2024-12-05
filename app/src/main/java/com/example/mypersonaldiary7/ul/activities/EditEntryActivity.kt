package com.example.mypersonaldiary7.ul.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mypersonaldiary7.R
import com.example.mypersonaldiary7.data.model.DiaryEntries
import com.example.mypersonaldiary7.ul.viewmodels.DiaryEntriesViewModel
import java.util.Date

class EditEntryActivity : AppCompatActivity() {

    private lateinit var entryEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var clipButton: ImageButton
    private lateinit var selectedImageView: ImageView
    private lateinit var viewModel: DiaryEntriesViewModel

    private var entryId: Int = -1
    private var entryTitle: String = ""
    private var entryDate: Long = 0L
    private var entryImageUri: String? = null
    private var selectedImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Permission to access photos is required!", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_entry)

        // Requesting permission to work with files
        requestStoragePermission()

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[DiaryEntriesViewModel::class.java]

        // Initializing UI components
        initViews()

        // get data from Intent and fill in the fields
        fetchIntentData()

        // Setting up button handlers
        setClickListeners()
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissionLauncher.launch(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
    }

    private fun initViews() {
        entryEditText = findViewById(R.id.entryEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)
        clipButton = findViewById(R.id.clipButton)
        selectedImageView = findViewById(R.id.selectedImageView)
    }

    private fun fetchIntentData() {
        entryId = intent.getIntExtra("ENTRY_ID", -1)
        entryTitle = intent.getStringExtra("ENTRY_TITLE") ?: ""
        entryDate = intent.getLongExtra("ENTRY_DATE", 0L)
        entryImageUri = intent.getStringExtra("ENTRY_IMAGE_URI")

        // Checking the correctness of the data
        if (entryId == -1 || entryDate == 0L) {
            showErrorAndFinish("Mistake: the record could not be uploaded")
            return
        }

        // Setting the data in the UI
        entryEditText.setText(entryTitle)
        entryImageUri?.let {
            selectedImageUri = Uri.parse(it)
            selectedImageView.visibility = View.VISIBLE
            selectedImageView.setImageURI(selectedImageUri)
        }
    }

    private fun setClickListeners() {
        // The Home button
        findViewById<ImageButton>(R.id.homeButton).setOnClickListener {
            navigateTo(MainActivity::class.java)
        }

        // The "Add Entry" button
        findViewById<ImageButton>(R.id.addButton).setOnClickListener {
            navigateTo(AddEntryActivity::class.java)
        }

        // The Calendar button
        findViewById<ImageButton>(R.id.calendarButton).setOnClickListener {
            navigateTo(CalendarActivity::class.java)
        }

        // The "Select image" button
        clipButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            pickImageLauncher.launch(intent)
        }

        // The "Save record" button
        saveButton.setOnClickListener {
            saveEntry()
        }

        // The "Delete entry" button
        deleteButton.setOnClickListener {
            deleteEntry()
        }
    }

    private fun saveEntry() {
        val updatedTitle = entryEditText.text.toString().trim()

        if (updatedTitle.isNotBlank()) {
            val updatedEntry = DiaryEntries(
                id = entryId,
                title = updatedTitle,
                createdAt = Date(entryDate),
                imageUri = selectedImageUri?.toString()
            )

            viewModel.updateDiaryEntry(updatedEntry)
            Toast.makeText(this, "The record has been successfully updated", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "The text of the record cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteEntry() {
        viewModel.deleteDiaryEntry(entryId)
        Toast.makeText(this, "The record was successfully deleted", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun showErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    try {
                        contentResolver.takePersistableUriPermission(
                            selectedImageUri!!,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        selectedImageView.visibility = View.VISIBLE
                        selectedImageView.setImageURI(selectedImageUri)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Image upload error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}
