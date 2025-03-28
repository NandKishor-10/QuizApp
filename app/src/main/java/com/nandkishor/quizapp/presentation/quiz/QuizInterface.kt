package com.nandkishor.quizapp.presentation.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nandkishor.quizapp.presentation.quiz.components.OptionButton
import com.nandkishor.quizapp.presentation.state.QuizState
import com.nandkishor.quizapp.util.characterCodeDecoder

@Composable
fun QuizInterface(
    qNumber: Int,
    quizState: QuizState,
    onOptionSelected: (Int) -> Unit,
    onOptionUnselected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val question = characterCodeDecoder(quizState.quiz?.question ?: "")
    val options = quizState.shuffledOptions?.map { option -> characterCodeDecoder(option) }

    Column(
        modifier = modifier
            .fillMaxSize(1f)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Text(text = "$qNumber. ", style = MaterialTheme.typography.headlineSmall)
            Text(text = question, style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        options?.forEachIndexed { index, option ->
            OptionButton(
                option = option,
                selected = quizState.selectedOptions == index,
                onOptionSelected = { onOptionSelected(index) },
                onOptionUnselected = { onOptionUnselected(index) }
            )
        }
    }
}