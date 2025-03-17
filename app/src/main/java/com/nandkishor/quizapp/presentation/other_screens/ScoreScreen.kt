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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.app.DataStoreManager
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.navigation.HomescreenWithDrawer
import com.nandkishor.quizapp.util.calculatePercentage
import com.nandkishor.quizapp.util.formatDouble

@Composable
fun ScoreScreen(score: Int, totalQuestions: Int, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val scoreInDouble = calculatePercentage(score, totalQuestions)
        val context = LocalContext.current
        val dataStoreManager = remember { DataStoreManager(context) }
        val existingScore by dataStoreManager.getHighestScore().collectAsState(0.0)
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = MaterialTheme.colorScheme.background
        )
        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colorScheme.background
        )

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
                text = "${formatDouble(scoreInDouble)} %",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimensions.FifteenDP))

            Text(
                text = "You answered $score out of $totalQuestions correctly.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensions.FifteenDP))

            Text(
                text = scoreMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensions.TwentyFiveDP))

            Button(onClick = {
                navController.navigate(HomescreenWithDrawer) {
                    popUpTo(HomescreenWithDrawer) {inclusive = true}
                }
            }) {
                Text("Restart Quiz")
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScoreScreenPrev() {
    ScoreScreen(
        10,
        10,
        rememberNavController()
    )
}