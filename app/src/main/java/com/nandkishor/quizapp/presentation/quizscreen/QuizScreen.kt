package com.nandkishor.quizapp.presentation.quizscreen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.common.Lists.categories
import com.nandkishor.quizapp.presentation.common.SecureScreen
import com.nandkishor.quizapp.presentation.otherscreens.ErrorScreen
import com.nandkishor.quizapp.presentation.otherscreens.ShimmerQuizInterface
import com.nandkishor.quizapp.presentation.quizscreen.components.PreviousAndNextButtons
import com.nandkishor.quizapp.presentation.quizscreen.components.QuizBar

@Composable
fun QuizScreen(
    category: String?,
    noOfQuestions: Int,
    difficulty: String?,
    type: String?,
    event: (EventQuizScreen) -> Unit,
    state: QuizScreenState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var noOfOptions by remember{ mutableIntStateOf(0) }

    LaunchedEffect (Unit) {
        noOfOptions = if (type == "Multiple Choice") 4 else 2
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
            QuizBar(
                category = category.toString(),
                noOfQuestions = noOfQuestions,
                difficulty = difficulty.toString(),
                navController = navController
            )
        }
    ) { innerPadding ->
        if (quizFetched(state = state, noOfOptions = noOfOptions, innerPadding = innerPadding)) {
            val pagerState = rememberPagerState { state.quizState.size }

            SecureScreen {
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = Dimensions.TenDP),
                    verticalAlignment = Alignment.CenterVertically
                ) { index ->
                    QuizInterface(
                        qNumber = index + 1,
                        quizState = state.quizState[index],
                        onOptionSelected = { selectedIndex ->
                            event(EventQuizScreen.SetSelectedOption(index, selectedIndex))

                            state.userAnswers[index] =
                                state.quizState[index].shuffledOptions?.get(selectedIndex) ?: ""
                            Log.d("userAnswers", "${state.userAnswers}")
                        },
                        onOptionUnselected = {
                            event(EventQuizScreen.SetSelectedOption(index, -1))
                        }
                    )
                }
            }

            PreviousAndNextButtons(
                pagerState = pagerState,
                noOfQuestions = noOfQuestions,
                state = state,
                navController = navController
            )
        }
    }
}

@Composable
fun quizFetched(state: QuizScreenState, noOfOptions: Int, innerPadding: PaddingValues): Boolean {
    return when {
        state.isLoading -> {
            ShimmerQuizInterface(noOfOptions, innerPadding)
            false
        }
        state.quizState.isNotEmpty() == true -> {
            true
        }
        state.error.isNotEmpty() -> {
            ErrorScreen(state.error, innerPadding)
            false
        }
        else -> {
            false
        }
    }
}