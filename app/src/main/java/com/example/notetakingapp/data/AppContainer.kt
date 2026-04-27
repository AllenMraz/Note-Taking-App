package com.example.notetakingapp.data

import android.content.Context

interface AppContainer { // interface that has the value to NoteRepository
    val noteRepository : NoteRepository
}

//  calls AppContainer to create a noteRepository
class AppDataContainer(private val context: Context) : AppContainer {

    override val noteRepository: NoteRepository by lazy{
        OfflineNoteRepository(NoteListDatabase.getDatabase(context).noteDao())
    }
}