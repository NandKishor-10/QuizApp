package com.nandkishor.quizapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandkishor.quizapp.util.Resource
import com.nandkishor.quizapp.domain.usecase.GetQuizzesUseCases
import com.nandkishor.quizapp.data.remote.models.Result
import com.nandkishor.quizapp.presentation.state.EventQuizScreen
import com.nandkishor.quizapp.presentation.state.QuizScreenState
import com.nandkishor.quizapp.presentation.state.QuizState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class QuizViewModel(private val getQuizzesUseCases: GetQuizzesUseCases) : ViewModel() {

    private val _quizList = MutableStateFlow(QuizScreenState())
    val quizList = _quizList

    fun onEvent(event: EventQuizScreen) {

        when (event) {
            is EventQuizScreen.GetQuizzes -> {
                getQuizzes(
                    noOfQuestion = event.noOfQuestion,
                    category = event.category,
                    difficulty = event.difficulty,
                    type = event.type,
                )
            }

            is EventQuizScreen.SetSelectedOption -> {
                updateQuizStateList(event.quizStateIndex, event.selectedOption)
            }
        }
    }

    private fun updateQuizStateList(quizStateIndex: Int, selectedOption: Int) {
        val updatedQuizStateList = quizList.value.quizState.toMutableList()
        updatedQuizStateList[quizStateIndex] = updatedQuizStateList[quizStateIndex].copy(selectedOptions = selectedOption)
        _quizList.value = quizList.value.copy(quizState = updatedQuizStateList)
        Log.d(
            "Answer",
            "C ${updatedQuizStateList[quizStateIndex].quiz?.correctAnswer ?: "N/A"} | " +
                "S ${if (selectedOption != -1) updatedQuizStateList[quizStateIndex].shuffledOptions?.getOrNull(selectedOption) 
                    else "Unselected"}"
        )
    }

    private fun getQuizzes(
        noOfQuestion: Int?,
        category: Int?,
        difficulty: String?,
        type: String?
    ) {
        viewModelScope.launch {
            getQuizzesUseCases(
                amount = noOfQuestion,
                category = category,
                difficulty = difficulty,
                type = type
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _quizList.value = QuizScreenState(isLoading = true)
                    }
                    is Resource.Success -> {
                        val listOfQuizState: List<QuizState> = getListOfQuizState(resource.data)
                        _quizList.value = QuizScreenState(quizState = listOfQuizState)
                    }
                    is Resource.Error -> {
                        _quizList.value = QuizScreenState(error = resource.message.toString())
                    }
                }
            }
        }
    }

    private fun getListOfQuizState(data: List<Result>?): List<QuizState> {
        val listOfQuizState = mutableListOf<QuizState>()
        data?.forEach { quiz ->
            val shuffledOptions = mutableListOf<String>().apply {
                add(quiz.correctAnswer)
                addAll(quiz.incorrectAnswers)
                shuffle()
            }
            listOfQuizState.add(QuizState(quiz, shuffledOptions, -1))
        }
        return listOfQuizState
    }
}