package com.nandkishor.quizapp.presentation.otherscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.nav_graph.HomeScreen

@Composable
fun ScoreScreen(score: Int, totalQuestions: Int, navController: NavController) {
    val scoreMessage = when {
        score == totalQuestions -> "🎉 Perfect! You're a quiz master!"
        score >= totalQuestions / 2 -> "😊 Great job! Keep practicing!"
        else -> "😕 Keep trying! You’ll get better!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.TwentyFiveDP),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(Dimensions.FifteenDP))

        Text(
            text = "Your Score",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "$score / $totalQuestions",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(Dimensions.FifteenDP))

        Text(
            text = scoreMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimensions.TwentyFiveDP))

        Button(onClick = {
            navController.navigate(HomeScreen) {
                popUpTo(HomeScreen) {inclusive = true}
            }
        }) {
            Text("Restart Quiz")
        }
    }
}