package com.nandkishor.quizapp.presentation.other_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.app.DataStoreManager
import com.nandkishor.quizapp.presentation.navigation.HomescreenWithDrawer
import com.nandkishor.quizapp.presentation.navigation.ReviewScreenArgs
import com.nandkishor.quizapp.util.calculatePercentage
import com.nandkishor.quizapp.util.formatDouble

@Composable
fun ScoreScreen(
    score: Int,
    totalQuestions: Int,
    quizStateJson: String?,
    navController: NavController
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primaryContainer
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scoreInDouble = calculatePercentage(score, totalQuestions)
        val context = LocalContext.current
        val dataStoreManager = remember { DataStoreManager(context) }
        val existingScore by dataStoreManager.getHighestScore().collectAsState(0.0)

        LaunchedEffect(existingScore, scoreInDouble) {
            if (scoreInDouble > existingScore)
                dataStoreManager.updateScore(scoreInDouble)
        }

        val scoreMessage = when {
            score == totalQuestions -> "🎉 Perfect! You're a quiz master!"
            score >= totalQuestions / 2 -> "😊 Great job! Keep practicing!"
            else -> "😕 Keep trying! You’ll get better!"
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quiz Completed!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Your Score",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${formatDouble(scoreInDouble)} %",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "You answered $score out of $totalQuestions correctly.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = scoreMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick = {
                navController.navigate(HomescreenWithDrawer) {
                    popUpTo(HomescreenWithDrawer) {inclusive = true}
                }
            }) {
                Text("Restart Quiz")
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick = {
                navController.navigate(ReviewScreenArgs(
                    score = score,
                    totalQuestions = totalQuestions,
                    quizStateJson = quizStateJson
                ))
            }) {
                Text("Review Answers")
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScoreScreenPrev() {
    val quizStateJson = "{\"error\":\"\",\"isLoading\":false,\"quizState\":[{\"quiz\":{\"category\":\"Geography\",\"correctAnswer\":\"Wien\",\"difficulty\":\"medium\",\"incorrectAnswers\":[\"Z\\u0026uuml;rich\",\"Frankfurt\",\"Wien\"],\"question\":\"Which city is the capital of Switzerland?\",\"type\":\"multiple\"},\"selectedOptions\":1,\"shuffledOptions\":[\"Wien\",\"Bern\",\"Frankfurt\",\"Z\\u0026uuml;rich\"]},{\"quiz\":{\"category\":\"General Knowledge\",\"correctAnswer\":\"19\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"20\",\"12\",\"15\"],\"question\":\"On a dartboard, what number is directly opposite No. 1?\",\"type\":\"multiple\"},\"selectedOptions\":0,\"shuffledOptions\":[\"19\",\"20\",\"12\",\"15\"]},{\"quiz\":{\"category\":\"Entertainment: Film\",\"correctAnswer\":\"False\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"True\"],\"question\":\"The word \\u0026quot;Inception\\u0026quot; came from the 2010 blockbuster hit \\u0026quot;Inception\\u0026quot;.\",\"type\":\"boolean\"},\"selectedOptions\":0,\"shuffledOptions\":[\"False\",\"True\"]}],\"userAnswers\":{\"0\":\"Bern\",\"1\":\"19\",\"2\":\"False\"}}"
    ScoreScreen(
        score = 10,
        totalQuestions = 10,
        quizStateJson = quizStateJson,
        navController = rememberNavController()
    )
}