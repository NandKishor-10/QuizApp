package com.nandkishor.quizapp.util

import android.text.Html
import com.google.gson.Gson
import com.nandkishor.quizapp.presentation.state.QuizScreenState

fun calculatePercentage(score: Int, totalQuestions: Int) =
    if (totalQuestions != 0) (score.toDouble() / totalQuestions) * 100
    else 0.0

fun formatDouble(value: Double) =
    if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        "%.2f".format(value)
    }

fun characterCodeDecoder(input: String) =
    Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()

val gson = Gson()

fun QuizScreenState.toJson(): String? = gson.toJson(this)

fun String.toQuizScreenState(): QuizScreenState? = gson.fromJson(this, QuizScreenState::class.java)
