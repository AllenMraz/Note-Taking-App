package com.example.notetakingapp.home

import android.accessibilityservice.GestureDescription
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingapp.R
import com.example.notetakingapp.data.Note
import com.example.notetakingapp.data.NoteRepository
import com.example.notetakingapp.data.UserPreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    noteRepository: NoteRepository,
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        noteRepository.getAllNoteStream().map { notes -> HomeUiState(notes) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class HomeUiState(val noteList: List<Note> = listOf())

