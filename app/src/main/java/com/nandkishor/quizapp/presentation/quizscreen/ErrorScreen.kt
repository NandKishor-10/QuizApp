package com.nandkishor.quizapp.presentation.quizscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.R.drawable.warning

@Composable
fun ErrorScreen(
    error: String,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = warning), // Replace with your image resource
            contentDescription = null
        )
        Text(
            text = error,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun Prev() {
//    ErrorScreen(
//        error = "No Internet Connection",
////        innerPadding =
//    )
//}