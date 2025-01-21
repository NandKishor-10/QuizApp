package com.nandkishor.quizapp.data.repository

import com.nandkishor.quizapp.data.remote.QuizApi
import com.nandkishor.quizapp.domian.Result
import com.nandkishor.quizapp.domian.QuizRepository

class QuizRepositoryImpl(
    private val quizApi: QuizApi
): QuizRepository {

    override suspend fun getQuizzes(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): List<Result> {
        return quizApi.getQuizzes(amount, category, difficulty, type).results
    }

}