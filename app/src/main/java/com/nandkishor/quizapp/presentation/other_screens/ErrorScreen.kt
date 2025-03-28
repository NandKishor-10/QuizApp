package com.nandkishor.quizapp.presentation.other_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nandkishor.quizapp.R.drawable.no_internet
import com.nandkishor.quizapp.R.drawable.warning
import com.nandkishor.quizapp.ui.theme.Red

@Composable
fun ErrorScreen(
    error: String,
    modifier: Modifier = Modifier
) {
    val noInternetError = "Unable to resolve host \"opentdb.com\": No address associated with hostname"
    val noInternet = "No Internet Connection"
    val otherError = "Failed to establish connection.\nTry Again"
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource( id =
                if (error == noInternetError) no_internet
                else warning
            ),
            contentDescription = "Error",
            tint = Red,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )
        Text(
            text = if (error == noInternetError) noInternet else otherError,
            style = if (screenWidth <= 400.dp) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Prev() {
    ErrorScreen(
        error = "Unable to resolve host \"opentdb.com\": No address associated with hostname",
    )
}