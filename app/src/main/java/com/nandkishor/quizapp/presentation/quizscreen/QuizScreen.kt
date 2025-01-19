package com.nandkishor.quizapp.presentation.quizscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.common.Lists.categories
import com.nandkishor.quizapp.presentation.quizscreen.components.PreviousAndNextButtons
import com.nandkishor.quizapp.presentation.quizscreen.components.QuizBar

@Composable
fun QuizScreen(
    category: String?,
    noOfQuestions: Int,
    difficulty: String?,
    type: String?,
    event: (EventQuizScreen) -> Unit,
    state: StateQuizScreen,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect (key1 = Unit) {
        val difficultyModified = when(difficulty) {
            "Hard" -> "hard"
            "Medium" -> "medium"
            else -> "easy"
        }
        val typeModified = when(type) {
            "Multiple Choice" -> "multiple"
            else -> "boolean"
        }
        event(EventQuizScreen.GetQuizzes(
            noOfQuestion = noOfQuestions,
            category = categories.toMap()[category],
            difficulty = difficultyModified,
            type = typeModified
        ))
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column{
                QuizBar(
                    category = if ("" == category) "Any Category"
                    else category.toString(),
                    noOfQuestions = noOfQuestions,
                    difficulty = if ("" == difficulty) "Mixed"
                    else difficulty.toString(),
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        if (quizFetched(state = state, innerPadding = innerPadding)) {
            val pagerState = rememberPagerState { state.quizState.size }

            HorizontalPager(
                state = pagerState,
                modifier = modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = Dimensions.TenDP),
                verticalAlignment = Alignment.CenterVertically
            ) { index ->
                QuizInterface(
                    qNumber = index + 1,
                    quizState = state.quizState[index],
                    onOptionSelected = { selectedIndex ->
                        event(EventQuizScreen.SetSelectedOption(index, selectedIndex))
                    },
                    onOptionUnselected = {
                        event(EventQuizScreen.SetSelectedOption(index, -1))
                    }
                )
            }
            PreviousAndNextButtons(
                pagerState = pagerState,
                noOfQuestions = noOfQuestions,
                state = state
            )
        }
    }
}

@Composable
fun quizFetched(state: StateQuizScreen, innerPadding: PaddingValues): Boolean {
    return when {
        state.isLoading -> {
            ShimmerQuizInterface(innerPadding = innerPadding)
            false
        }
        state.quizState.isNotEmpty() == true -> {
            true
        }
        else -> {
            Text(text = state.error, color = MaterialTheme.colorScheme.error)
            false
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun Prev() {
//    QuizScreen(
//        "Science & Tech",
//        10,
//        "Hard",
//        "multiChoice"
//    )
//}