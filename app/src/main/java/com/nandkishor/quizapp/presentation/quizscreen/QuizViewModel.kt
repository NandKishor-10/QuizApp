package com.nandkishor.quizapp.presentation.quizscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandkishor.quizapp.common.Resource
import com.nandkishor.quizapp.domian.model.Result
import com.nandkishor.quizapp.domian.usecases.GetQuizzesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCases: GetQuizzesUseCases
): ViewModel() {

    private val _quizList = MutableStateFlow(StateQuizScreen())
    val quizList = _quizList

    fun onEvent(event: EventQuizScreen) {
        when(event) {
            is EventQuizScreen.GetQuizzes -> {
                getQuizzes(
                    noOfQuestion = event.noOfQuestion,
                    category = event.category,
                    difficulty = event.difficulty,
                    type = event.type
                )
            }
            else -> {}
        }
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
            ).collect{ resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _quizList.value = StateQuizScreen(isLoading = true)
                    }
                    is Resource.Success -> {
                        val listOfQuizState: List<QuizState> = getListOfQuizState(resource.data)
                        _quizList.value = StateQuizScreen(quizState = listOfQuizState)
                    }
                    is Resource.Error -> {
                        _quizList.value = StateQuizScreen(error = resource.message.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getListOfQuizState(data: List<Result>?): List<QuizState> {
        val listOfQuizState = mutableListOf<QuizState>()
        for (quiz in data!!) {
            val shuffledOptions = mutableListOf<String>().apply {
                add(quiz.correct_answer)
                addAll(quiz.incorrect_answers)
                shuffle()
            }
            listOfQuizState.add(QuizState(quiz, shuffledOptions, -1))
        }
        return listOfQuizState
    }
}