package com.nandkishor.quizapp.presentation.main_activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.nav_graph.AppNavigation
import com.nandkishor.quizapp.presentation.nav_graph.QuizScreen
import com.nandkishor.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()
        setContent {
            QuizAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun ElevatedNextButton(
    buttonText: String = "Button Text",
    modifier: Modifier = Modifier,
    noOfQuestions: String,
    category: String,
    difficulty: String,
    type: String,
    navController: NavController
) {
    val context = LocalContext.current
    ElevatedButton(
        onClick = {
            if (noOfQuestions.isNotBlank() && type.isNotBlank()) {
                navController.navigate(
                    QuizScreen(
                        noOfQuestions = noOfQuestions.toInt(),
                        category = category,
                        difficulty = difficulty,
                        type = type
                    )
                )
            } else if (noOfQuestions.isBlank()) {
                Toast.makeText(context, "Number of Questions can't be null", Toast.LENGTH_SHORT)
                    .show()
            } else if (type.isBlank()) {
                Toast.makeText(context, "Type can't be null", Toast.LENGTH_SHORT).show()
            }
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth(0.75f)
    ) {
        Text(buttonText)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Generate Quiz Button",
            modifier = modifier
                .size(ButtonDefaults.IconSize)
                .clip(RoundedCornerShape(Dimensions.HundredDP))
                .background(color = MaterialTheme.colorScheme.primary),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}