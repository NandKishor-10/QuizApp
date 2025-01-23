package com.nandkishor.quizapp.app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nandkishor.quizapp.presentation.navigation.AppNavigation
import com.nandkishor.quizapp.ui.theme.QuizAppTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()
        setContent {
            QuizAppTheme {
                AppNavigation()
            }
        }
    }
}

