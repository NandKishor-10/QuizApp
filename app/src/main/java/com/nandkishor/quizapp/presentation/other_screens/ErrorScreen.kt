package com.nandkishor.quizapp.presentation.other_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.nandkishor.quizapp.R.drawable.warning
import com.nandkishor.quizapp.presentation.common.Dimensions

@Composable
fun ErrorScreen(
    error: String,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val noInternetError = "Unable to resolve host \"opentdb.com\": No address associated with hostname"
    Column(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(innerPadding)
            .padding(Dimensions.TenDP),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = warning),
            contentDescription = null,
            modifier = modifier.fillMaxWidth(.5f)
                .aspectRatio(1f)
        )
        Text(
            text = if (error == noInternetError) "No Internet Connection" else error,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun Prev() {
//    ErrorScreen(
//        error = "Unable to resolve host \"opentdb.com\": No address associated with hostname",
//        innerPadding = PaddingValues(24.dp)
//    )
//}