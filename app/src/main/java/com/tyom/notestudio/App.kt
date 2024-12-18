package com.tyom.notestudio

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}