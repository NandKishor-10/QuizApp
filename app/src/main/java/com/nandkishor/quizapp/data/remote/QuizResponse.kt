package com.nandkishor.quizapp.data.remote

import com.nandkishor.quizapp.domian.Result

data class QuizResponse(
    val response_code: Int,
    val results: List<Result>
)