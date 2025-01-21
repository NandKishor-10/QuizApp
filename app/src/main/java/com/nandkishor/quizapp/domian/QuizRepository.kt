package com.nandkishor.quizapp.domian

interface QuizRepository {
    suspend fun getQuizzes(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): List<Result>
}