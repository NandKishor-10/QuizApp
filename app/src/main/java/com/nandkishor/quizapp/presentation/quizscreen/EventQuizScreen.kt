package com.nandkishor.quizapp.presentation.quizscreen

sealed class EventQuizScreen{
    data class GetQuizzes(
        val noOfQuestion: Int?,
        val category: Int?,
        val difficulty: String?,
        val type: String?
    ): EventQuizScreen()
}


