package com.nandkishor.quizapp.util

fun calculatePercentage(score: Int, totalQuestions: Int) =
    if (totalQuestions != 0) (score.toDouble() / totalQuestions) * 100
    else 0.0

fun formatDouble(value: Double) =
    if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        "%.2f".format(value)
    }