package com.example.notetakingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
// A Dao for Note
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun  update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from tasks WHERE id = :id")
    fun getNotes(id: Int): Flow<Note>

    @Query("Select * from tasks Order By title ASC")
    fun getAllNotes(): Flow<List<Note>>
}