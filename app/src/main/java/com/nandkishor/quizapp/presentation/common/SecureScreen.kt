package com.nandkishor.quizapp.presentation.common

import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

@Composable
fun SecureScreen(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val window = (context as? ComponentActivity)?.window
    val view = LocalView.current

    DisposableEffect(view) {
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    content()
}