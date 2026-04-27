package com.example.notetakingapp.data

import kotlinx.coroutines.flow.Flow
// a interface for a repository for note
interface NoteRepository {

    fun getAllNoteStream(): Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}