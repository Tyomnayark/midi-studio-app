package com.tyom.notestudio

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}