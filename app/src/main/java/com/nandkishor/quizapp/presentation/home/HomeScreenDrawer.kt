package com.nandkishor.quizapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nandkishor.quizapp.app.DataStoreManager
import com.nandkishor.quizapp.util.formatDouble
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDrawer() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(context) }
    val score by dataStoreManager.getHighestScore().collectAsState(0.0)
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

        Column (
            modifier = Modifier.width(width = screenWidth * 0.52f)
        ) {
            val themeList = listOf("Light theme", "System default", "Dark theme")
            var expanded by remember { mutableStateOf(false) }
            var currentTheme = dataStoreManager.getThemeFlow().collectAsState("System default")

            Text(text = "  App Theme: ", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = currentTheme.value,
                    onValueChange = {  },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                            modifier = Modifier.menuAnchor(type = MenuAnchorType.SecondaryEditable)
                        )
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable),
                    singleLine = true,
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    shape = MaterialTheme.shapes.medium
                ) {
                    themeList.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text, style = MaterialTheme.typography.bodyLarge) },
                            onClick = {
                                coroutineScope.launch {
                                    dataStoreManager.updateThemePref(text)
                                }
                                expanded = false
                            },
                        )
                    }
                }
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