package com.nandkishor.quizapp.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("category") val category: String,
    @SerialName("correct_answer") val correctAnswer: String,
    @SerialName("difficulty") val difficulty: String,
    @SerialName("incorrect_answers") val incorrectAnswers: List<String>,
    @SerialName("question") val question: String,
    @SerialName("type") val type: String
)

@Serializable
data class Question(
    val text: String,
    val correctAnswer: String,
    val allAnswers: List<String>,
    val category: String,
    val difficulty: String,
    val type: String,
    val userAnswer: String? = null
) {
    companion object {
        fun fromResult(result: Result, shuffledOptions: List<String>): Question {
            return Question(
                result.question,
                result.correctAnswer,
                shuffledOptions,
                result.category,
                result.difficulty,
                result.type
            )
        }
    }
}