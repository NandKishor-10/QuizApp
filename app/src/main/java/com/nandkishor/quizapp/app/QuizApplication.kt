package com.nandkishor.quizapp.app

import android.app.Application
import com.nandkishor.quizapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuizApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin dependency injection
        startKoin {
            androidContext(this@QuizApplication)
            modules(appModule) // Inject your Koin modules here
        }
    }
}