package com.nandkishor.quizapp.data.remote.dto

import com.nandkishor.quizapp.domian.model.Result

data class QuizResponse(
    val response_code: Int,
    val results: List<Result>
)