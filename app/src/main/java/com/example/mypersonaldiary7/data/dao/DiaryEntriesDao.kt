package com.example.mypersonaldiary7.data.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mypersonaldiary7.data.model.DiaryEntries

@Dao
interface DiaryEntriesDao {

        // Getting all the records
        @Query("SELECT * FROM DiaryEntries")
        fun getAllDiaryEntries(): LiveData<List<DiaryEntries>>

        // Adding an entry
        @Insert
        fun addDiaryEntries(diaryEntry: DiaryEntries)

        // Deleting an entry by ID
        @Query("DELETE FROM DiaryEntries WHERE id = :id")
        fun deleteDiaryEntry(id: Int)

        // updating the record
         @Update
        fun updateDiaryEntry(diaryEntry: DiaryEntries)

}




