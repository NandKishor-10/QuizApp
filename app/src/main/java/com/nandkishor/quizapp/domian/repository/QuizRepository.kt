package com.nandkishor.quizapp.domian.repository

import com.nandkishor.quizapp.domian.model.Result

interface QuizRepository {
    suspend fun getQuizzes(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): List<Result>
}