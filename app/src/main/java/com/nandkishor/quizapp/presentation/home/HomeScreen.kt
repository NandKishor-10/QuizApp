package com.nandkishor.quizapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.presentation.common.Lists
import com.nandkishor.quizapp.presentation.common.TopHeader
import com.nandkishor.quizapp.presentation.home.components.DropdownMenu
import com.nandkishor.quizapp.presentation.home.components.ElevatedNextButton

@Composable
fun HomeScreen(
    navController: NavController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    boxModifier: Modifier
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primaryContainer
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background
    )

    var noOfQuestions by remember { mutableStateOf("10") }
    var category by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = { TopHeader(
            title = "Quiz App",
            drawerState = drawerState,
            showDrawerButton = true,
            navController = navController
        ) }
    ) { innerPadding ->

        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            DropdownMenu(label = "Number of Questions", defaultValue = noOfQuestions, isNecessary = true, lists = Lists.amounts, onDropdownClick = {noOfQuestions = it})
            DropdownMenu(label = "Select Category", lists = Lists.categories.map{it.first}, onDropdownClick = {category = it})
            DropdownMenu(label = "Select Difficulty", lists = Lists.difficulties, onDropdownClick = {difficulty = it})
            DropdownMenu(label = "Select Type", lists = Lists.type, onDropdownClick = {type = it})

            HorizontalDivider(color = Color.Transparent)
            HorizontalDivider(color = Color.Transparent)

            ElevatedNextButton(
                noOfQuestions = noOfQuestions,
                category = category,
                difficulty = difficulty,
                type = type,
                navController = navController
            )
        }
    }
    Box(modifier = modifier.then(boxModifier))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(
        navController = rememberNavController(),
        drawerState = rememberDrawerState(DrawerValue.Closed),
        boxModifier = Modifier
    )
}