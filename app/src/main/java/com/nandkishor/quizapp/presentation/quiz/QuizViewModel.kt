package com.nandkishor.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandkishor.quizapp.common.Resource
import com.nandkishor.quizapp.domian.Result
import com.nandkishor.quizapp.domian.GetQuizzesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCases: GetQuizzesUseCases
): ViewModel() {

    private val _quizList = MutableStateFlow(QuizScreenState())
    val quizList = _quizList

    fun onEvent(event: EventQuizScreen) {
        if (event is EventQuizScreen.GetQuizzes) {
            getQuizzes(
                noOfQuestion = event.noOfQuestion,
                category = event.category,
                difficulty = event.difficulty,
                type = event.type
            )
        }
        else if (event is EventQuizScreen.SetSelectedOption) {
            updateQuizStateList(event.quizStateIndex, event.selectedOption)
        }
    }

    private fun updateQuizStateList(quizStateIndex: Int, selectedOption: Int) {
        val updatedQuizStateList = quizList.value.quizState.toMutableList()
        updatedQuizStateList[quizStateIndex] = updatedQuizStateList[quizStateIndex].copy(selectedOptions = selectedOption)
        _quizList.value = quizList.value.copy(quizState = updatedQuizStateList)
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
                        _quizList.value = QuizScreenState(isLoading = true)
                    }
                    is Resource.Success -> {
                        val listOfQuizState: List<QuizState> = getListOfQuizState(resource.data)
                        _quizList.value = QuizScreenState(quizState = listOfQuizState)
                    }
                    is Resource.Error -> {
                        _quizList.value = QuizScreenState(error = resource.message.toString())
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