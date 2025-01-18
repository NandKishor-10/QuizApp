package com.nandkishor.quizapp.presentation.quizscreen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.quizscreen.components.OptionButton

@Composable
fun QuizInterface(
    qNumber: Int,
    quizState: QuizState,
    modifier: Modifier = Modifier
) {
    val question = characterCodeDecoder(quizState.quiz?.question!!)
    val options = quizState.shuffledOptions?.map {option -> characterCodeDecoder(option)}
    var selectedOption by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(Dimensions.TenDP)
//            .padding(top = Dimensions.FiftyDP)
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
            options?.forEach { option ->
                OptionButton(
                    option = option,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it }
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