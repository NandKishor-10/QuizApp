package com.nandkishor.quizapp.domain.usecase

import android.util.Log
import com.nandkishor.quizapp.util.Resource
import com.nandkishor.quizapp.data.remote.models.Result
import com.nandkishor.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetQuizzesUseCases(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(
        amount: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())

        try {
            val response = quizRepository.getQuizzes(amount, category, difficulty, type)
            emit(response) // Directly emit the response (which is already a Resource)
        } catch (e: Exception) {
            Log.e("error", e.toString())
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}
