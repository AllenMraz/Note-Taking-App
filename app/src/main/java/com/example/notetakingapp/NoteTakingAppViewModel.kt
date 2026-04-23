package com.example.notetakingapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notetakingapp.data.UserPreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteTakingAppViewModel(val userPreferenceRepository: UserPreferenceRepository) : ViewModel() {
    val appUiState: StateFlow<AppUiState> = userPreferenceRepository.isDarkMode.map { isDarkMode ->
        AppUiState(isDarkMode)
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_00),
        initialValue = runBlocking {
            AppUiState(
                isDarkMode = userPreferenceRepository.isDarkMode.first()
            )
        })
    fun selectTheme(isDarkMode: Boolean){
        viewModelScope.launch {
            userPreferenceRepository.saveDisplayPreference(isDarkMode)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NoteTakingApplication)
                NoteTakingAppViewModel(application.userPreferenceRepository)
            }
        }
    }
}

data class AppUiState(var isDarkMode: Boolean = true,
                       val toggleContentDescription: Int =
                            if(isDarkMode) R.string.light_mode_toggle else R.string.dark_mode_toggle
)