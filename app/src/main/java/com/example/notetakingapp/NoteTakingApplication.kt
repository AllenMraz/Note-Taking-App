package com.example.notetakingapp

import android.app.Application
import com.example.notetakingapp.data.AppContainer
import com.example.notetakingapp.data.AppDataContainer

class NoteTakingApplication: Application() {
    lateinit var  container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}