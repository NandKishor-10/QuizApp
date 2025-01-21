package com.nandkishor.quizapp.presentation.quiz

import android.text.Html
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.quiz.components.OptionButton

@Composable
fun QuizInterface(
    qNumber: Int,
    quizState: QuizState,
    onOptionSelected: (Int) -> Unit,
    onOptionUnselected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val question = characterCodeDecoder(quizState.quiz?.question!!)
    val options = quizState.shuffledOptions?.map {option -> characterCodeDecoder(option)}

    Box(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(Dimensions.TenDP)
            .selectableGroup(),
    ) {
        Column (
            modifier = modifier
                .selectableGroup()
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                Text(text = "$qNumber. ", style = MaterialTheme.typography.headlineSmall)
                Text(text = question, style = MaterialTheme.typography.headlineSmall)
            }
            Spacer(modifier = modifier.padding(vertical = Dimensions.TenDP))
            options?.forEachIndexed { index,option ->
                OptionButton(
                    option = option,
                    selected = quizState.selectedOptions == index,
                    onOptionSelected = { onOptionSelected(index)},
                    onOptionUnselected = { onOptionUnselected(index) }
                )
            }
        }
    }
}


fun characterCodeDecoder(input: String) =
    Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()

//@Preview(showSystemUi = true)
//@Composable
//private fun QuizInterfacePrev() {
//    QuizInterface(
//        qNumber = 0,
//        question = "Question?",
//        options = listOf("option 1", "option 2", "option 3", "option 4")
//    )
//}