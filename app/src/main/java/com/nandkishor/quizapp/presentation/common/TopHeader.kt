package com.nandkishor.quizapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nandkishor.quizapp.ui.theme.Black
import com.nandkishor.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeader(
    title: String = "Some Error Occurred",
    navController: NavController,
    showBackButton: Boolean = false,
    drawerState: DrawerState = rememberDrawerState( DrawerValue.Closed),
    showDrawerButton: Boolean = false,
    totalQuestions: Int = 10,
    difficulty: String = "",
    showQuizInfo: Boolean = false,
    score: Int = 5,
    showScore: Boolean = false
    ) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Column {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = Dimensions.TwentyFiveDP,
                        bottomEnd = Dimensions.TwentyFiveDP
                    )
                ),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            },
            actions = {
                if (showDrawerButton) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Open profile"
                        )
                    }
                }

                if (showScore) {
                    FractionText(score, totalQuestions)
                }
            },
            scrollBehavior = scrollBehavior
        )
        if (showQuizInfo) {
            Spacer(Modifier.padding(vertical = Dimensions.FiveDP))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Questions: $totalQuestions",
                    modifier = Modifier.padding(start = Dimensions.TenDP)
                )
                Text(
                    "Difficulty: $difficulty",
                    modifier = Modifier.padding(end = Dimensions.TenDP)
                )
            }
            Spacer(Modifier.padding(Dimensions.OneDP))
            HorizontalDivider()
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun FractionText(score: Int, totalQuestions: Int) {
    BasicText(
        text = buildAnnotatedString {
            appendAnnotatedText(score.toString(), BaselineShift(0.5f), 18.sp)

            appendAnnotatedText("/", BaselineShift(0f), 24.sp, Color.Gray)

            appendAnnotatedText(totalQuestions.toString(), BaselineShift(-0.3f), 18.sp)
        }
    )
}

fun AnnotatedString.Builder.appendAnnotatedText(
    text: String,
    baselineShift: BaselineShift,
    fontSize: TextUnit,
    color: Color = Black
) {
    withStyle(
        style = SpanStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            baselineShift = baselineShift,
            color = color
        )
    ) {
        append(text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HeaderPrevLight() {
    QuizAppTheme(darkTheme = false) {
        TopHeader(navController = rememberNavController())
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HeaderPrevDark() {
//    QuizAppTheme(darkTheme = true) { TopHeader(navController = rememberNavController()) }
//}