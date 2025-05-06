package com.cmp.microhabit.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cmp.microhabit.ui.screen.onboarding.screens.GoalSelectionScreen
import com.cmp.microhabit.ui.screen.onboarding.screens.NotificationPermissionScreen
import com.cmp.microhabit.ui.screen.onboarding.screens.PersonalizationScreen
import com.cmp.microhabit.ui.screen.onboarding.screens.TimeCommitmentScreen
import com.cmp.microhabit.ui.screen.onboarding.screens.WelcomeScreen
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.AppRoutes
import com.cmp.microhabit.utils.OnboardingRoutes


fun NavGraphBuilder.onBoardingGraph(navController: NavController, viewModel: OnboardingViewmodel) {
    composable(OnboardingRoutes.WELCOME) {
        WelcomeScreen { navController.navigate(OnboardingRoutes.GOAL) }
    }
    composable(OnboardingRoutes.GOAL) {
        GoalSelectionScreen(
            viewmodel = viewModel,
            onNext = { navController.navigate(OnboardingRoutes.TIME) },
        )
    }
    composable(OnboardingRoutes.TIME) {
        TimeCommitmentScreen(
            viewmodel = viewModel,
            onNext = { navController.navigate(OnboardingRoutes.PERSONALIZATION) },
        )
    }
    composable(OnboardingRoutes.PERSONALIZATION) {
        PersonalizationScreen(
            viewmodel = viewModel,
            onNext = { navController.navigate(OnboardingRoutes.NOTIFICATIONS) },
        )
    }
    composable(OnboardingRoutes.NOTIFICATIONS) {
        NotificationPermissionScreen(
            onComplete = {
                viewModel.setOnboardingDone()
                navController.navigate(AppRoutes.HOME) {
                    popUpTo(OnboardingRoutes.WELCOME) {
                        inclusive = true
                    }
                }
            },
        )
    }
}