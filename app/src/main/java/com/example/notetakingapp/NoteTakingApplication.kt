package com.example.notetakingapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.notetakingapp.data.AppContainer
import com.example.notetakingapp.data.AppDataContainer
import com.example.notetakingapp.data.UserPreferenceRepository

private const val DISPLAY_PREFERENCE_NAME = "display_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DISPLAY_PREFERENCE_NAME
)
class NoteTakingApplication: Application() {
    lateinit var  container: AppContainer
    lateinit var userPreferenceRepository: UserPreferenceRepository

    override fun onCreate() {
        super.onCreate()
        userPreferenceRepository = UserPreferenceRepository(dataStore)
        container = AppDataContainer(this)
    }
}


