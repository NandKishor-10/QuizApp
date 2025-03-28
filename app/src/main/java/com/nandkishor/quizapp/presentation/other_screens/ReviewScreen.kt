package com.nandkishor.quizapp.presentation.other_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.presentation.common.SecureScreen
import com.nandkishor.quizapp.presentation.common.TopHeader
import com.nandkishor.quizapp.presentation.quiz.components.PreviousAndNextButtons
import com.nandkishor.quizapp.presentation.state.QuizState
import com.nandkishor.quizapp.ui.theme.DarkGreen
import com.nandkishor.quizapp.ui.theme.Green
import com.nandkishor.quizapp.ui.theme.LightGreen
import com.nandkishor.quizapp.ui.theme.LightRed
import com.nandkishor.quizapp.ui.theme.Red
import com.nandkishor.quizapp.util.characterCodeDecoder
import com.nandkishor.quizapp.util.toQuizScreenState


@Composable
fun ReviewScreen(
    score: Int,
    totalQuestions: Int,
    quizStateJson: String?,
    navController: NavController,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primaryContainer
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background
    )

    Scaffold (
        topBar = {
            TopHeader(
                title = "Review Answers",
                navController = navController,
                showBackButton = true,
                score = score,
                totalQuestions = totalQuestions,
                showScore = true
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        if (quizStateJson.isNullOrEmpty()) {
            ErrorScreen(
                error = "No Quiz Data Found"
            )
        }

        val quizState = quizStateJson?.toQuizScreenState()!!

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val pagerState = rememberPagerState(pageCount =  { quizState.quizState.size })

            SecureScreen {
                HorizontalPager(
                    state = pagerState,
                    verticalAlignment = Alignment.Top,
                    modifier= Modifier.weight(1f)
                ) { index ->
                    ReviewInterface(
                        qNumber = index + 1,
                        quizState = quizState.quizState[index]
                    )
                }
            }
            PreviousAndNextButtons(
                pagerState = pagerState,
                noOfQuestions = totalQuestions,
                state = quizState,
                navController = navController,
            )
        }
    }
}

@Composable
fun ReviewInterface(
    qNumber: Int,
    quizState: QuizState,
    modifier: Modifier = Modifier,
) {
    val question = characterCodeDecoder(quizState.quiz?.question ?: "")
    val options = quizState.shuffledOptions?.map { option -> characterCodeDecoder(option) }

    Column(
        modifier = modifier
            .padding(
                top = 20.dp,
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        // Displaying the question
        Row {
            Text(text = "$qNumber. ", style = MaterialTheme.typography.headlineSmall)
            Text(text = question, style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        // Displaying the options
        options?.forEachIndexed { index, option ->
            OptionButtonReview(
                option = option,
                selected = quizState.selectedOptions == index,
                selectedOption = quizState.quiz?.correctAnswer ?: ""
            )
        }
    }
}

@Composable
fun OptionButtonReview(
    option: String,
    selected: Boolean,
    selectedOption: String
) {
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = when {
            option == selectedOption -> Green
            selected -> Red
            else -> MaterialTheme.colorScheme.primary
        },
        unselectedColor = MaterialTheme.colorScheme.outline
    )

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(
                when {
                    selectedOption == option -> LightGreen
                    selected -> LightRed
                    else -> MaterialTheme.colorScheme.background
                }
            )
            .border(
                width = when {
                    option == selectedOption -> 3.dp
                    selected -> 2.dp
                    else -> 1.dp
                },
                color = when {
                    selectedOption == option -> Green
                    selected -> Red
                    else -> MaterialTheme.colorScheme.secondary
                },
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = radioButtonColors
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = option, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.weight(1f))
        if (selectedOption == option || selected) {
            Icon(
                imageVector = if (selectedOption == option) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = if (selectedOption == option) DarkGreen else Red
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun OptionPrev1() {
//    OptionButtonReview(
//        "Let say",
//        false,
//        "Let say"
//    )
//}
//@Preview(showBackground = true)
//@Composable
//private fun OptionPrev2() {
//    OptionButtonReview(
//        "Let say",
//        true,
//        "Let say"
//    )
//}
//@Preview(showBackground = true)
//@Composable
//private fun OptionPrev3() {
//    OptionButtonReview(
//        "Let say",
//        false,
//        "Let. say"
//    )
//}
//@Preview(showBackground = true)
//@Composable
//private fun OptionPrev4() {
//    OptionButtonReview(
//        "Let say",
//        true,
//        "Let. say"
//    )
//}
@Preview(showBackground = true)
@Composable
private fun ReviewScreenPreview() {
    val quizStateJson = "{\"error\":\"\",\"isLoading\":false,\"quizState\":[{\"quiz\":{\"category\":\"Geography\",\"correctAnswer\":\"Wien\",\"difficulty\":\"medium\",\"incorrectAnswers\":[\"Z\\u0026uuml;rich\",\"Frankfurt\",\"Wien\"],\"question\":\"Which city is the capital of Switzerland?\",\"type\":\"multiple\"},\"selectedOptions\":1,\"shuffledOptions\":[\"Wien\",\"Bern\",\"Frankfurt\",\"Z\\u0026uuml;rich\"]},{\"quiz\":{\"category\":\"General Knowledge\",\"correctAnswer\":\"19\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"20\",\"12\",\"15\"],\"question\":\"On a dartboard, what number is directly opposite No. 1?\",\"type\":\"multiple\"},\"selectedOptions\":0,\"shuffledOptions\":[\"19\",\"20\",\"12\",\"15\"]},{\"quiz\":{\"category\":\"Entertainment: Film\",\"correctAnswer\":\"False\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"True\"],\"question\":\"The word \\u0026quot;Inception\\u0026quot; came from the 2010 blockbuster hit \\u0026quot;Inception\\u0026quot;.\",\"type\":\"boolean\"},\"selectedOptions\":0,\"shuffledOptions\":[\"False\",\"True\"]}],\"userAnswers\":{\"0\":\"Bern\",\"1\":\"19\",\"2\":\"False\"}}"
    ReviewScreen(
        score = 2,
        totalQuestions = 3,
        quizStateJson = quizStateJson,
        navController = rememberNavController()
    )
}