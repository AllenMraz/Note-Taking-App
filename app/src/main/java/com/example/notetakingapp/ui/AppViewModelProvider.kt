package com.example.notetakingapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notetakingapp.NoteTakingApplication
import com.example.notetakingapp.home.HomeViewModel
import com.example.notetakingapp.ui.note.NoteDetails
import com.example.notetakingapp.ui.note.NoteEntryViewModel
import com.example.notetakingapp.ui.theme.NoteTakingAppTheme

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteEntryViewModel(noteTakingApplication().container.noteRepository)
        }

        initializer {
            HomeViewModel(noteTakingApplication().container.noteRepository)
        }
    }
}

fun CreationExtras.noteTakingApplication(): NoteTakingApplication =
    (this [AndroidViewModelFactory.APPLICATION_KEY] as NoteTakingApplication)