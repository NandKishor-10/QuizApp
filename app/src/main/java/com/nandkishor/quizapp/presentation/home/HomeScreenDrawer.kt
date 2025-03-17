package com.nandkishor.quizapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.R
import com.nandkishor.quizapp.app.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun HomeScreenDrawer() {
    val score = 3.5f
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(context) }
    val isDarkModeEnabled by remember { dataStoreManager.getThemeFlow() }.collectAsState(false)
    val statusBarColor = MaterialTheme.colorScheme.primaryContainer
    val navigationBarColor = MaterialTheme.colorScheme.background
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = statusBarColor
    )
    systemUiController.setNavigationBarColor(
        color = navigationBarColor
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
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
            Spacer(modifier = Modifier.padding(vertical = 24.dp))
            Text(
                text = "$score",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }

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

@Preview(showBackground = true)
@Composable
private fun Prev() {
    HomeScreenDrawer()
}