package com.example.uceyecomposeversion.model.room

import android.app.Application
import androidx.room.Room

class AppApplication(application: Application) : Application(){
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}