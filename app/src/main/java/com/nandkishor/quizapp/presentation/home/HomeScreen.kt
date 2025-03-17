package com.nandkishor.quizapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nandkishor.quizapp.presentation.common.Dimensions
import com.nandkishor.quizapp.presentation.common.Lists
import com.nandkishor.quizapp.presentation.home.components.DropdownMenu
import com.nandkishor.quizapp.presentation.home.components.ElevatedNextButton
import com.nandkishor.quizapp.presentation.home.components.HeaderBar

@Composable
fun HomeScreen(navController: NavController, drawerState: DrawerState, modifier: Modifier = Modifier) {

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
        topBar = { HeaderBar(navController, drawerState) },
    ) { innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimensions.TwentyFiveDP)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(Dimensions.TwentyFiveDP ))
            DropdownMenu(label = "Number of Questions", defaultValue = noOfQuestions, isNecessary = true, lists = Lists.amounts, onDropdownClick = {noOfQuestions = it})
            Spacer(modifier = Modifier.padding(Dimensions.TwentyFiveDP ))
            DropdownMenu(label = "Select Category", lists = Lists.categories.map{it.first}, onDropdownClick = {category = it})
            Spacer(modifier = Modifier.padding(Dimensions.TwentyFiveDP ))
            DropdownMenu(label = "Select Difficulty", lists = Lists.difficulties, onDropdownClick = {difficulty = it})
            Spacer(modifier = Modifier.padding(Dimensions.TwentyFiveDP ))
            DropdownMenu(label = "Select Type", lists = Lists.type, onDropdownClick = {type = it})
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Dimensions.FiftyDP),
            contentAlignment = Alignment.BottomCenter
        ) {
            ElevatedNextButton(
                noOfQuestions = noOfQuestions,
                category = category,
                difficulty = difficulty,
                type = type,
                navController = navController
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun HomeScreenPrev() {
//    HomeScreen(rememberNavController())
//}