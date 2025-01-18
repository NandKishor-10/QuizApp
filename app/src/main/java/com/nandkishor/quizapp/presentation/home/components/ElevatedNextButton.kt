package com.nandkishor.quizapp.presentation.home.components

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.nav_graph.QuizScreen

@Composable
fun ElevatedNextButton(
    buttonText: String = "Button Text",
    noOfQuestions: String,
    category: String,
    difficulty: String,
    type: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ElevatedButton(
        onClick = {
            if (noOfQuestions.isNotBlank()) {
                navController.navigate(
                    QuizScreen(
                        noOfQuestions = noOfQuestions.toInt(),
                        category = category,
                        difficulty = difficulty,
                        type = type
                    )
                )
            } else {
                Toast.makeText(context, "Number of Questions can't be null", Toast.LENGTH_SHORT).show()
            }
        },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier.fillMaxWidth(0.75f)
    ) {
        Text(buttonText)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Generate Quiz Button",
            modifier = modifier
                .size(ButtonDefaults.IconSize)
                .clip(RoundedCornerShape(Dimensions.HundredDP))
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

//@Preview
//@Composable
//private fun Prev() {
//    ElevatedNextButton(
//        "Next ",
//        "10",
//        "",
//        "",
//        "multiple Choice",
//        rememberNavController()
//    )
//}