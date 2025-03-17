package com.nandkishor.quizapp.data.remote

import android.util.Log
import com.nandkishor.quizapp.data.remote.models.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class QuizApi(private val client: HttpClient) {

    suspend fun getQuizzes(
        amount: Int? = 10,
        category: Int? = null,
        difficulty: String? = null,
        type: String? = null
    ): QuizResponse {
        return try {
            client.get("https://opentdb.com/api.php") {
                url {
                    parameters.append("amount", amount.toString())
                    category?.let { parameters.append("category", it.toString()) }
                    difficulty?.let { parameters.append("difficulty", it) }
                    type?.let { parameters.append("type", it) }
                }
            }.body()
        } catch (e: Exception) {
            Log.e("Error e", e.message.toString())
            throw Exception("Failed to fetch quizzes: ${e.localizedMessage}")
        }

    }
}

@Serializable
data class QuizResponse(
    @SerialName("response_code") val responseCode: Int,
    @SerialName("results") val results: List<Result>
)