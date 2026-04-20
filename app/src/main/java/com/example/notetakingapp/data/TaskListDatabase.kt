package com.example.notetakingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskListDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var Instance: TaskListDatabase? = null

        fun getDatabase(context: Context): TaskListDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskListDatabase::class.java, "task_database")
                    .build().also { Instance = it }
            }
        }
    }
}