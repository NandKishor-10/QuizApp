package com.nandkishor.quizapp.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nandkishor.quizapp.presentation.navigation.QuizScreenArgs

@Composable
fun ElevatedNextButton(
    noOfQuestions: String,
    category: String,
    difficulty: String,
    type: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    ElevatedButton(
        onClick = {
            if (noOfQuestions.isNotBlank()) {
                navController.navigate(
                    QuizScreenArgs(
                        noOfQuestions = noOfQuestions.toInt(),
                        category = category,
                        difficulty = difficulty,
                        type = type
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    "Number of Questions can't be null",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier.fillMaxWidth(
            if (screenWidth >= 500.dp) 0.33f
                    else 0.67f)
            .height(40.dp)
    ) {
        Text("Generate Quiz")
        Spacer(modifier.padding(horizontal = 5.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Generate Quiz",
            modifier = Modifier
                .size(ButtonDefaults.IconSize)
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Prev() {
    ElevatedNextButton(
        "10",
        "",
        "",
        "multiple Choice",
        rememberNavController()
    )
}