package com.nandkishor.quizapp.presentation.quizscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nandkishor.quizapp.presentation.common.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizBar(
    category: String,
    noOfQuestions: Int,
    difficulty: String,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column{
        CenterAlignedTopAppBar(
            modifier = modifier
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
                    text = if (category.isNotBlank()) category else "Any Category",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
        Spacer(modifier.padding(vertical = Dimensions.TenDP))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Questions: $noOfQuestions",
                modifier = modifier.padding(start = Dimensions.TenDP)
            )
            Text(
                "Difficulty: ${if (difficulty.isNotBlank()) difficulty else "Mixed"}",
                modifier = modifier.padding(end = Dimensions.TenDP)
            )
        }
        Spacer(modifier.padding(Dimensions.OneDP))
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    QuizBar(
        category = "Science & Tech",
        noOfQuestions = 10,
        difficulty = "",
        navController = rememberNavController()
    )
}