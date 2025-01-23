package com.nandkishor.quizapp.data.repository

import com.nandkishor.quizapp.util.Resource
import com.nandkishor.quizapp.data.remote.QuizApi
import com.nandkishor.quizapp.data.remote.models.Result

class QuizRepositoryImpl(private val quizApi: QuizApi): QuizRepository {
    override suspend fun getQuizzes(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): Resource<List<Result>> {
        return try {
            val response = quizApi.getQuizzes(amount, category, difficulty, type)
            Resource.Success(response.results)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An Error occurred")
        }
    }
}
