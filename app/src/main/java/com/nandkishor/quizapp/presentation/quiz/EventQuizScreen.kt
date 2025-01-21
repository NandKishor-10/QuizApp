package com.nandkishor.quizapp.presentation.quiz

sealed class EventQuizScreen{
    data class GetQuizzes(
        val noOfQuestion: Int?,
        val category: Int?,
        val difficulty: String?,
        val type: String?
    ): EventQuizScreen()

    data class SetSelectedOption(
        val quizStateIndex: Int,
        val selectedOption: Int
    ): EventQuizScreen()
}


