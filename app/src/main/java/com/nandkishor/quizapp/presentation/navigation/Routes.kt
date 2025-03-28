package com.nandkishor.quizapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomescreenWithDrawer

@Serializable
data class QuizScreenArgs(
    val noOfQuestions: Int,
    val category: String?,
    val difficulty: String?,
    val type: String?
)

@Serializable
data class ScoreScreenArgs(
    val score: Int,
    val noOfQuestions: Int,
    val quizStateJson: String?
)

@Serializable
data class ErrorScreenArgs(
    val error: String
)

@Serializable
data class ReviewScreenArgs(
    val score: Int,
    val totalQuestions: Int,
    val quizStateJson: String?
)

