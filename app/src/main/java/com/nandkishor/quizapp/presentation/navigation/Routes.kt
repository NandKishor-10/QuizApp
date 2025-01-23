package com.nandkishor.quizapp.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
object HomeScreen

@Serializable
data class QuizScreen(
    val noOfQuestions: Int,
    val category: String?,
    val difficulty: String?,
    val type: String?
)

@Serializable
data class ScoreScreen(
    val score: Int,
    val noOfQuestions: Int
)
