package com.example.notetakingapp.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingapp.data.NoteRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NoteRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    private val noteId: Int = checkNotNull(savedStateHandle[NoteEditDestination.noteIdArg])

    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getNoteStream(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState(true)
        }
    }

    /**
     * Update the note in the [NoteRepository]'s data source
     */
    suspend fun updateNote() {
        if (validateInput(noteUiState.noteDetails)) {
            notesRepository.updateNote(noteUiState.noteDetails.toNote())
        }
    }

    /**
     * Updates the [noteUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }

    suspend fun deleteNote() {
        notesRepository.deleteNote(noteUiState.noteDetails.toNote())
    }

}

data class NoteEditUiState(
    val noteDetails: NoteDetails = NoteDetails()
)