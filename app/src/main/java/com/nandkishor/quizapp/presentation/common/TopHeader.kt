package com.nandkishor.quizapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    noOfQuestions: Int = 0,
    difficulty: String = "",
    showQuizInfo: Boolean = false
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
                    "Questions: $noOfQuestions",
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