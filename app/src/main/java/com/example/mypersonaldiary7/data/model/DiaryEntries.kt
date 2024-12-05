package com.example.mypersonaldiary7.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
 data class DiaryEntries (
  @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var title : String,
    var createdAt : Date,
    var imageUri: String? = null
)


