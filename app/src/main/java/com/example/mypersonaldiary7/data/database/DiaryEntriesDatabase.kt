package com.example.mypersonaldiary7.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mypersonaldiary7.data.model.DiaryEntries
import com.example.mypersonaldiary7.data.dao.DiaryEntriesDao
import com.example.mypersonaldiary7.data.converters.Convertes


@Database(entities = [DiaryEntries::class], version = 2)
@TypeConverters(Convertes::class)
abstract class DiaryEntriesDatabase : RoomDatabase() {

    abstract fun getDiaryEntriesDao(): DiaryEntriesDao

    companion object {
        const val NAME = "DiaryEntries_DB"

        @Volatile
        private var instance: DiaryEntriesDatabase? = null

        // creating a migration
        public val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adding a new column to the table
                database.execSQL("ALTER TABLE DiaryEntries ADD COLUMN imageUri TEXT")
            }
        }

        // Getting a database instance
        fun getInstance(context: Context): DiaryEntriesDatabase {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryEntriesDatabase::class.java,
                    NAME
                )
                    .addMigrations(MIGRATION_1_2) // Enabling migration
                    .build()
                instance = db
                db
            }
        }
    }
}
