package com.nandkishor.quizapp.presentation.quizscreen.components

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.presentation.common.Dimensions
import kotlinx.coroutines.launch

@Composable
fun PreviousAndNextButtons(
    pagerState: PagerState,
    noOfQuestions: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = Dimensions.FiftyDP),
        verticalAlignment = Alignment.Bottom,
    ) {
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = modifier.weight(.2f))
        Button(
            onClick = {
                coroutineScope.launch{ pagerState.animateScrollToPage(pagerState.currentPage - 1) }
            },
            modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (pagerState.currentPage == 0) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
            Text(text = "Previous")
        }
        Spacer(modifier = modifier.weight(.2f))
        Button(onClick = {
            if (pagerState.currentPage == noOfQuestions - 1) TODO()
            else coroutineScope.launch{ pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = modifier.weight(1f)
        ) {
            Text(text = if (pagerState.currentPage == noOfQuestions - 1) "Submit"
                else "Next")
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
        Spacer(modifier = modifier.weight(.2f))
    }
}

//@Preview
//@Composable
//private fun Prev() {
//    PreviousAndNextButtons()
//}