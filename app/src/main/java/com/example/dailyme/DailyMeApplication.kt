package com.example.dailyme

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DailyMeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            FirebaseApp.initializeApp(this)
        } catch (_: Exception) {
            // Fallback for cases where google-services.json isn't being picked up correctly
            val options = FirebaseOptions.Builder()
                .setApiKey("AIzaSyCtAug0awUzGQc888ncb_izQzqR2lF6Sw8")
                .setApplicationId("1:991958363555:android:e5f1de7286d062fe893e16")
                .setProjectId("pennywise-7d08a")
                .setStorageBucket("pennywise-7d08a.firebasestorage.app")
                .build()
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this, options)
            }
        }
    }
}
