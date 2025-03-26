package com.nandkishor.quizapp.presentation.other_screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.common.SecureScreen
import com.nandkishor.quizapp.presentation.common.TopHeader
import com.nandkishor.quizapp.presentation.quiz.components.PreviousAndNextButtons
import com.nandkishor.quizapp.presentation.state.QuizState
import com.nandkishor.quizapp.util.characterCodeDecoder
import com.nandkishor.quizapp.util.toQuizScreenState
import com.nandkishor.quizapp.ui.theme.Green
import com.nandkishor.quizapp.ui.theme.Red


@Composable
fun ReviewScreen(
    score: Int,
    totalQuestions: Int,
    quizStateJson: String?,
    navController: NavController,
    modifier: Modifier = Modifier
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
                showBackButton = true,
                navController = navController
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        if (quizStateJson.isNullOrEmpty()) {
            ErrorScreen(
                error = "No Quiz Data Found",
                innerPadding = innerPadding
            )
        }

        val quizState = quizStateJson?.toQuizScreenState()!!

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val pagerState = rememberPagerState(pageCount =  { quizState.quizState.size })

            SecureScreen {
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = Dimensions.TenDP),
                    verticalAlignment = Alignment.CenterVertically
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
                navController = navController
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.TenDP)
            .selectableGroup(),
    ) {
        Column(
            modifier = modifier
                .selectableGroup()
                .verticalScroll(rememberScrollState())
        ) {
            // Displaying the question
            Row {
                Text(text = "$qNumber. ", style = MaterialTheme.typography.headlineSmall)
                Text(text = question, style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = modifier.padding(vertical = Dimensions.TenDP))

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
            .padding(Dimensions.FiveDP)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = when {
                    option == selectedOption -> Dimensions.ThreeDP
                    selected -> Dimensions.TwoDP
                    else -> Dimensions.OneDP
                },
                color = when {
                    selectedOption == option -> Green
                    selected -> Red
                    else -> MaterialTheme.colorScheme.secondary
                },
                shape = MaterialTheme.shapes.medium
            )
            .padding(Dimensions.TenDP),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = radioButtonColors
        )
        Spacer(modifier = Modifier.padding(Dimensions.FiveDP))
        Text(text = option, style = MaterialTheme.typography.bodyLarge)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReviewScreenPreview() {
    val quizStateJson = "{\"error\":\"\",\"isLoading\":false,\"quizState\":[{\"quiz\":{\"category\":\"Geography\",\"correctAnswer\":\"Bern\",\"difficulty\":\"medium\",\"incorrectAnswers\":[\"Z\\u0026uuml;rich\",\"Frankfurt\",\"Wien\"],\"question\":\"Which city is the capital of Switzerland?\",\"type\":\"multiple\"},\"selectedOptions\":1,\"shuffledOptions\":[\"Wien\",\"Bern\",\"Frankfurt\",\"Z\\u0026uuml;rich\"]},{\"quiz\":{\"category\":\"General Knowledge\",\"correctAnswer\":\"19\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"20\",\"12\",\"15\"],\"question\":\"On a dartboard, what number is directly opposite No. 1?\",\"type\":\"multiple\"},\"selectedOptions\":0,\"shuffledOptions\":[\"19\",\"20\",\"12\",\"15\"]},{\"quiz\":{\"category\":\"Entertainment: Film\",\"correctAnswer\":\"False\",\"difficulty\":\"easy\",\"incorrectAnswers\":[\"True\"],\"question\":\"The word \\u0026quot;Inception\\u0026quot; came from the 2010 blockbuster hit \\u0026quot;Inception\\u0026quot;.\",\"type\":\"boolean\"},\"selectedOptions\":0,\"shuffledOptions\":[\"False\",\"True\"]}],\"userAnswers\":{\"0\":\"Bern\",\"1\":\"19\",\"2\":\"False\"}}"
    ReviewScreen(
        score = 2,
        totalQuestions = 3,
        quizStateJson = quizStateJson,
        navController = rememberNavController()
    )
}