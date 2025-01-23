package com.nandkishor.quizapp.presentation.state

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


