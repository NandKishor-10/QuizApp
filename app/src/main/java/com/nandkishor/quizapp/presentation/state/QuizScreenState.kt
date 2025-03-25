package com.nandkishor.quizapp.presentation.state

import com.nandkishor.quizapp.data.remote.models.Result
import kotlinx.serialization.Contextual

data class QuizScreenState (
    val isLoading: Boolean = false,
    @Contextual val quizState: List<QuizState> = emptyList(),
    val error: String = "",
    @Contextual val userAnswers: MutableMap<Int, String> = mutableMapOf(),
)

data class QuizState (
    val quiz: Result? = null,
    val shuffledOptions: List<String>? = emptyList(),
    val selectedOptions: Int? = -1
)