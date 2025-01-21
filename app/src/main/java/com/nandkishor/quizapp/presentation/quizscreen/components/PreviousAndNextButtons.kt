package com.nandkishor.quizapp.presentation.quizscreen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.nav_graph.ScoreScreen
import com.nandkishor.quizapp.presentation.quizscreen.QuizScreenState
import com.nandkishor.quizapp.ui.theme.Green
import kotlinx.coroutines.launch

@Composable
fun PreviousAndNextButtons(
    pagerState: PagerState,
    noOfQuestions: Int,
    state: QuizScreenState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    var score by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = Dimensions.FiftyDP),
        verticalAlignment = Alignment.Bottom,
    ) {
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = modifier.weight(.2f))
        Button(
            onClick = {
                coroutineScope.launch{ pagerState.animateScrollToPage(
                    pagerState.currentPage - 1,
                    animationSpec = tween(delayMillis = 250)
                ) }
            },
            modifier = modifier.weight(1f),
            enabled = pagerState.currentPage != 0
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
            Text(text = "Previous", textAlign = TextAlign.Center)
        }
        Spacer(modifier = modifier.weight(.2f))
        val context = LocalContext.current
        Button(onClick = {
            if (pagerState.currentPage == noOfQuestions - 1) {
                Toast.makeText(context, "Quiz Ends here", Toast.LENGTH_SHORT).show()
//                Log.d("submit", state.score.toString())
                score = onSubmit(state)
                navController.navigate(ScoreScreen(score, noOfQuestions))
//                {
//                    popUpTo(ScoreScreen) {inclusive = true}
//                }
            }
            else coroutineScope.launch{ pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                animationSpec = tween(delayMillis = 250)
            ) }
            },
            modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (pagerState.currentPage != noOfQuestions -1) MaterialTheme.colorScheme.primary
                else Green,
                contentColor = if (pagerState.currentPage != noOfQuestions -1) MaterialTheme.colorScheme.onPrimary
                else White
            )
        ) {
            Text(text = if (pagerState.currentPage != noOfQuestions - 1) "Next" else "Submit", textAlign = TextAlign.Center)
            Icon(
                imageVector = if (pagerState.currentPage != noOfQuestions - 1) Icons.AutoMirrored.Filled.KeyboardArrowRight
                else Icons.Default.Done,
                contentDescription = null
            )
        }
        Spacer(modifier = modifier.weight(.2f))
    }
}

fun onSubmit(state: QuizScreenState): Int {
    var score = 0
    for ((questionId, selectedOption) in state.userAnswers) {
        if (state.quizState[questionId].quiz?.correct_answer == selectedOption) {
            score++
        }
    }
    Log.d("score Submit", "$score")
    return score
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun Prev() {
//    PreviousAndNextButtons(
//        pagerState = //,
//        noOfQuestions = 10,
//        modifier = //
//    )
//}