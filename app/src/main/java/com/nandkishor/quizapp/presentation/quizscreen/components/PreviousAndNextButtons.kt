package com.nandkishor.quizapp.presentation.quizscreen.components

import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.ui.theme.Green
import com.nandkishor.quizapp.ui.theme.Indigo
import com.nandkishor.quizapp.ui.theme.Purple40
import kotlinx.coroutines.delay
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
                coroutineScope.launch{ pagerState.animateScrollToPage(
                    pagerState.currentPage - 1,
                    animationSpec = tween(delayMillis = 500)
                ) }
            },
            modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (pagerState.currentPage != 0) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
                contentColor = if (pagerState.currentPage != 0) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
            Text(text = "Previous")
        }
        Spacer(modifier = modifier.weight(.2f))
        val context = LocalContext.current
        Button(onClick = {
            if (pagerState.currentPage == noOfQuestions - 1) Toast.makeText(context, "Quiz Ends here",
                Toast.LENGTH_SHORT).show()
            else coroutineScope.launch{ pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                animationSpec = tween(delayMillis = 500)
            ) }
            },
            modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (pagerState.currentPage != noOfQuestions -1) MaterialTheme.colorScheme.inversePrimary
                else Green,
                contentColor = Color.White
            )
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

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun Prev() {
//    PreviousAndNextButtons(
//        pagerState = TODO(),
//        noOfQuestions = 10,
//        modifier = TODO()
//    )
//}