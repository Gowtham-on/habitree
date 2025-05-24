package com.cmp.microhabit.ui.navigation

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.screen.habits.screen.HabitsScreen
import com.cmp.microhabit.ui.screen.home.screens.HomeScreen
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.AppRoutes
import com.cmp.microhabit.utils.BottomTabItems
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.ShowLottieWithIterations

fun NavGraphBuilder.homeGraph() {
    composable(AppRoutes.HOME) {
        GetHomeScreens()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GetHomeScreens() {
    val tabNavController = rememberNavController()
    val viewmodel: HomeViewmodel = hiltViewModel()

    Scaffold(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F8F1),
                        Color(0xFFC6F5DE)
                    )
                )
            )
            .padding(WindowInsets.safeDrawing.asPaddingValues()),
        bottomBar = {
            BottomBar(tabNavController)
        }
    ) { padding ->
        NavHost(
            navController = tabNavController,
            startDestination = "Home",
        ) {
            composable("Home") { HomeScreen(viewmodel, tabNavController) }
            composable("Habits") { HabitsScreen(viewmodel) }
            composable("Insights") { Text("Insights") }
            composable("Profile") { Text("Profile") }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = remember {
        listOf(
            BottomTabItems(
                selectedIcon = R.raw.home_tab_lottie,
                canShowDot = false,
                route = "Home"
            ),
            BottomTabItems(
                selectedIcon = R.raw.habit_tab_lottie,
                canShowDot = false,
                route = "Habits",
                lottieSpeed = 2f
            ),
            BottomTabItems(
                selectedIcon = R.raw.insights_tab_lottie,
                canShowDot = false,
                route = "Insights",
                lottieSpeed = 3f
            ),
            BottomTabItems(
                selectedIcon = R.raw.profile_tab_lottie_two,
                canShowDot = false,
                route = "Profile",
                lottieSpeed = 10f
            )
        )
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    LaunchedEffect(currentBackStackEntry) {
        println("Current route in HomeScreen: ${currentBackStackEntry?.destination?.route}")
    }
    val haptic = LocalView.current

    Card(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .padding(horizontal = 12.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Row {
            items.forEach { item ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clickable(
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                }
                            },
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                ) {
                    ShowLottieWithIterations(
                        animationRes = item.selectedIcon,
                        iterations = 1,
                        play = currentRoute == item.route,
                        height = 30,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.primary.toArgb()
                        else
                            Color.Gray.toArgb(),
                        speed = item.lottieSpeed
                    )
                    SetVerticalGap(6)
                    Text(
                        item.route, style = MaterialTheme.typography.labelSmall,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                    )
                }
            }
        }
    }
}