package com.example.mypersonaldiary7.ul.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypersonaldiary7.MainApplication
import com.example.mypersonaldiary7.data.model.DiaryEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel for managing application data and business logic
class DiaryEntriesViewModel : ViewModel() {
    // Getting a DAO instance to interact with the database
    private val diaryEntriesDao = MainApplication.diaryEntriesDatabase.getDiaryEntriesDao()
    //  Live Data list of all diary entries from the database
    val diaryEntriesList: LiveData<List<DiaryEntries>> = diaryEntriesDao.getAllDiaryEntries()
    // Method for adding a new record to the database
    fun addDiaryEntry(diaryEntry: DiaryEntries) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryEntriesDao.addDiaryEntries(diaryEntry)
        }
    }
    // Method for deleting a record from the database by ID
    fun deleteDiaryEntry(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryEntriesDao.deleteDiaryEntry(id)
        }
    }
    // Method for updating an existing record in the database
    fun updateDiaryEntry(diaryEntry: DiaryEntries) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryEntriesDao.updateDiaryEntry(diaryEntry)
        }
    }
}
