package com.nandkishor.quizapp.presentation.quizscreen

import com.nandkishor.quizapp.domian.model.Result

data class QuizScreenState (
    val isLoading: Boolean = false,
    val quizState: List<QuizState> = emptyList(),
    val error: String = "",
    val userAnswers: MutableMap<Int, String> = mutableMapOf(),
    val score: Int = 0
)

data class QuizState (
    val quiz: Result? = null,
    val shuffledOptions: List<String>? = emptyList(),
    val selectedOptions: Int? = -1
)