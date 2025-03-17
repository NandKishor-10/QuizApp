package com.nandkishor.quizapp.app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nandkishor.quizapp.presentation.navigation.AppNavigation
import com.nandkishor.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val dataStoreManager = DataStoreManager(this)
        var darkThemeState: Boolean? = null

        runBlocking {
            darkThemeState = dataStoreManager.getThemeFlow().firstOrNull()
        }

        installSplashScreen().setKeepOnScreenCondition {
            darkThemeState == null
        }
        setContent {
            val darkTheme by dataStoreManager.getThemeFlow().collectAsState(darkThemeState?: false)

            QuizAppTheme(darkTheme = darkTheme) {
                AppNavigation()
            }
        }
    }
}

