package com.nandkishor.quizapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nandkishor.quizapp.R
import com.nandkishor.quizapp.app.DataStoreManager
import com.nandkishor.quizapp.util.formatDouble
import kotlinx.coroutines.launch

@Composable
fun HomeScreenDrawer() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(context) }
    val score by dataStoreManager.getHighestScore().collectAsState(0.0)
    val isDarkModeEnabled by remember { dataStoreManager.getThemeFlow() }.collectAsState(false)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(25.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High Score:",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Text(
                text = "${formatDouble(score)} %",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                coroutineScope.launch {
                    dataStoreManager.updateThemePref(!isDarkModeEnabled)
                }
            }
        ) {
            Text(text = "Dark Mode: ", color = MaterialTheme.colorScheme.onBackground)

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        dataStoreManager.updateThemePref(!isDarkModeEnabled)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isDarkModeEnabled) R.drawable.darkmode else R.drawable.lightmode
                    ),
                    contentDescription = null,
                    tint = if (isDarkModeEnabled) Color.White else Color.Black
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = screenWidth * 0.1f, bottom = 15.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "API Used: Open Trivia DB",
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall
                .copy(fontFamily = FontFamily.Monospace)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Prev() {
    HomeScreenDrawer()
}