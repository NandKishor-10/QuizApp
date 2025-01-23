package com.nandkishor.quizapp.presentation.state

import com.nandkishor.quizapp.data.remote.models.Result

data class QuizScreenState (
    val isLoading: Boolean = false,
    val quizState: List<QuizState> = emptyList(),
    val error: String = "",
    val userAnswers: MutableMap<Int, String> = mutableMapOf(),
)

data class QuizState (
    val quiz: Result? = null,
    val shuffledOptions: List<String>? = emptyList(),
    val selectedOptions: Int? = -1
)