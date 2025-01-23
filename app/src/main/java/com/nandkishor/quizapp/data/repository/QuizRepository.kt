package com.nandkishor.quizapp.data.repository

import com.nandkishor.quizapp.util.Resource
import com.nandkishor.quizapp.data.remote.models.Result

interface QuizRepository {
    suspend fun getQuizzes(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): Resource<List<Result>>
}