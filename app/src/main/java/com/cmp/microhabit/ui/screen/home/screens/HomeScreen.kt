package com.cmp.microhabit.ui.screen.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun HomeScreen(viewmodel: HomeViewmodel, navController: NavHostController) {
    val onboardingViewmodel: OnboardingViewmodel = hiltViewModel()
//    val userId = onboardingViewmodel.userData.value.id.toString()
    val userId = 6.toString()

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F8F1), // #e6f8f1
                        Color(0xFFC6F5DE)  // #c6f5de
                    )
                )
            )
            .padding(bottom = 7.dp)
            .padding(horizontal = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        HomeProgressIndicator(viewmodel)
        SetVerticalGap(25)
        HabitGarden(viewmodel, userId, navController)
        SetVerticalGap(100)
    }
}