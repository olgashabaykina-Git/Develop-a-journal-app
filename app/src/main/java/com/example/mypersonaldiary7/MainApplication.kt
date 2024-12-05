package com.example.mypersonaldiary7
import android.app.Application
import androidx.room.Room
import com.example.mypersonaldiary7.data.database.DiaryEntriesDatabase


class MainApplication : Application() {

    companion object {
        lateinit var diaryEntriesDatabase: DiaryEntriesDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // creating a database with a migration strategy
        diaryEntriesDatabase = Room.databaseBuilder(
            applicationContext,
            DiaryEntriesDatabase::class.java,
            DiaryEntriesDatabase.NAME
        )
            .addMigrations(DiaryEntriesDatabase.MIGRATION_1_2) // Adding Migration
            .fallbackToDestructiveMigration()
            .build()
    }
}



