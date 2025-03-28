package com.nandkishor.quizapp.presentation.quiz

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.presentation.common.Lists.categories
import com.nandkishor.quizapp.presentation.common.SecureScreen
import com.nandkishor.quizapp.presentation.common.TopHeader
import com.nandkishor.quizapp.presentation.navigation.ErrorScreenArgs
import com.nandkishor.quizapp.presentation.navigation.HomescreenWithDrawer
import com.nandkishor.quizapp.presentation.other_screens.ErrorScreen
import com.nandkishor.quizapp.presentation.quiz.components.PreviousAndNextButtons
import com.nandkishor.quizapp.presentation.state.EventQuizScreen
import com.nandkishor.quizapp.presentation.state.QuizScreenState

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
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primaryContainer
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background
    )

    var noOfOptions by remember{ mutableIntStateOf(0) }

    LaunchedEffect (Unit) {
        noOfOptions = if (type == "True/False") 2 else 4
        val difficultyModified = when(difficulty) {
            "Hard" -> "hard"
            "Medium" -> "medium"
            "Easy" -> "easy"
            else -> null
        }
        val typeModified = when(type) {
            "Multiple Choice" -> "multiple"
            "True/False" -> "boolean"
            else -> null
        }
        event(
            EventQuizScreen.GetQuizzes(
            noOfQuestion = noOfQuestions,
            category = categories.toMap()[category],
            difficulty = difficultyModified,
            type = typeModified
        ))
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopHeader(
                title = if (category!!.isNotBlank()) category else "Any Category",
                totalQuestions = noOfQuestions,
                difficulty = if (difficulty!!.isNotBlank()) difficulty else "Mixed",
                showBackButton = true,
                showQuizInfo = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        if (quizFetched(
                state = state,
                noOfOptions = noOfOptions,
                innerPadding = innerPadding,
                navController = navController)
        ) {
            val pagerState = rememberPagerState { state.quizState.size }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
            ) {
                SecureScreen {
                    HorizontalPager(
                        state = pagerState,
                        verticalAlignment = Alignment.Top,
                        modifier = modifier.weight(1f),
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
                            },
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(10.dp)
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
}

@Composable
fun quizFetched(
    state: QuizScreenState,
    noOfOptions: Int,
    innerPadding: PaddingValues,
    navController: NavController
): Boolean {
    return when {
        state.isLoading -> {
            ShimmerQuizInterface(noOfOptions, innerPadding)
            false
        }
        state.quizState.isNotEmpty() == true -> {
            true
        }
        state.error.isNotEmpty() -> {
            navController.navigate(ErrorScreenArgs) {
                popUpTo(HomescreenWithDrawer)
            }
            ErrorScreen(state.error)
            false
        }
        else -> {
            false
        }
    }
}