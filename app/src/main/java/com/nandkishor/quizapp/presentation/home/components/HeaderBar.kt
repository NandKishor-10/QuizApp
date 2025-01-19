package com.nandkishor.quizapp.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.presentation.common.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    CenterAlignedTopAppBar(
        modifier = modifier
            .clip(shape = RoundedCornerShape(bottomStart = Dimensions.TwentyFiveDP,
                bottomEnd = Dimensions.TwentyFiveDP)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                text = "Quiz App",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context, "Not Implemented", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Not Implemented", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Open profile"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HeaderBarPrev() {
    HeaderBar()
}