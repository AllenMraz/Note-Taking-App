package com.example.notetakingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteListDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var Instance: NoteListDatabase? = null

        fun getDatabase(context: Context): NoteListDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NoteListDatabase::class.java, "note_database")
                    .build().also { Instance = it }
            }
        }
    }
}