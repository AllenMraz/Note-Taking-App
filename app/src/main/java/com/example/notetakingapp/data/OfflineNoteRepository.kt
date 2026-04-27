package com.example.notetakingapp.data

import kotlinx.coroutines.flow.Flow
// offline repository that impliments note repository
class OfflineNoteRepository (private val noteDao: NoteDao): NoteRepository{
    override fun getAllNoteStream(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNoteStream(id: Int): Flow<Note?> = noteDao.getNotes(id)

    override suspend fun insertNote(note: Note) = noteDao.insert(note)

    override suspend fun deleteNote(note: Note) = noteDao.delete(note)

    override suspend fun updateNote(note: Note) = noteDao.update(note)

}
