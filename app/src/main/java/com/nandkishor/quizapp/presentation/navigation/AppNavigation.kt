package com.nandkishor.quizapp.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nandkishor.quizapp.presentation.home.HomeScreen
import com.nandkishor.quizapp.presentation.home.HomeScreenDrawer
import com.nandkishor.quizapp.presentation.other_screens.ScoreScreen
import com.nandkishor.quizapp.presentation.quiz.QuizScreen
import com.nandkishor.quizapp.presentation.state.QuizScreenState
import com.nandkishor.quizapp.presentation.viewmodel.QuizViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomescreenWithDrawer
    ) {
        composable<HomescreenWithDrawer> {
            Box {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp
                val translateX = screenWidth * 0.8f
                val coroutineScope = rememberCoroutineScope()

                val translateAnimate by animateDpAsState(
                    targetValue = if (drawerState.isOpen) translateX else 0.dp,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "Drawer animation"
                )

                HomeScreenDrawer()

                HomeScreen(
                    navController = navController,
                    drawerState = drawerState,
                    modifier = Modifier
                        .graphicsLayer {
                            this.translationX = -translateAnimate.toPx()
                            this.scaleX = if (drawerState.isOpen) 0.9f else 1f
                            this.scaleY = if (drawerState.isOpen) 0.9f else 1f
                        }
                        .clip(RoundedCornerShape(if (drawerState.isOpen) 32.dp else 0.dp))
                        .border(
                            width = if (drawerState.isOpen) 1.dp else 0.dp,
                            color = if (drawerState.isOpen) Color.Gray else Color.Transparent,
                            shape = RoundedCornerShape(if (drawerState.isOpen) 32.dp else 0.dp)
                        ),
                    boxModifier = Modifier
                        .fillMaxSize()
                        .then(if (drawerState.isOpen) {
                                Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                            } else {
                                Modifier // No clickable modifier when drawer is closed
                            }
                        )
                )
            }
        }

        composable<QuizScreenArgs> {
            val args = it.toRoute<QuizScreenArgs>()

            // Use Koin to inject the ViewModel
            val quizViewModel: QuizViewModel = koinViewModel()
            val state: QuizScreenState by quizViewModel.quizList.collectAsState()

            QuizScreen(
                noOfQuestions = args.noOfQuestions,
                category = args.category,
                difficulty = args.difficulty,
                type = args.type,
                event = { quizViewModel.onEvent(it) },
                state = state,
                navController = navController
            )
        }

        composable<ScoreScreenArgs> {
            val args = it.toRoute<ScoreScreenArgs>()

            ScoreScreen(
                score = args.score,
                totalQuestions = args.noOfQuestions,
                navController = navController
            )
        }
    }
}
