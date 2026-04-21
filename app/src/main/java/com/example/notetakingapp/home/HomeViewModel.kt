package com.example.notetakingapp.home

import com.example.notetakingapp.data.Note

class HomeViewModel {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val noteList: List<Note> = listOf())