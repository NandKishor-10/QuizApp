package com.nandkishor.quizapp.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nandkishor.quizapp.presentation.home.HomeScreen
import com.nandkishor.quizapp.presentation.quizscreen.QuizScreen
import com.nandkishor.quizapp.presentation.quizscreen.QuizViewModel
import com.nandkishor.quizapp.presentation.quizscreen.StateQuizScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(navController = navController)
        }

        composable<QuizScreen> {
            val args = it.toRoute<QuizScreen>()

            val quizViewModel: QuizViewModel = hiltViewModel()
            val state: StateQuizScreen by quizViewModel.quizList.collectAsState()

            QuizScreen(
                noOfQuestions = args.noOfQuestions,
                category = args.category,
                difficulty = args.difficulty,
                type = args.type,
                event = { quizViewModel.onEvent(it) },
                state = state,
                navController = navController
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NavigationPrev() {
    AppNavigation()
}