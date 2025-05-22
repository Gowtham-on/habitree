package com.cmp.microhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cmp.microhabit.ui.navigation.homeGraph
import com.cmp.microhabit.ui.navigation.onBoardingGraph
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.ui.theme.MicroHabitTheme
import com.cmp.microhabit.utils.AppRoutes
import com.cmp.microhabit.utils.OnboardingRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MicroHabitTheme (darkTheme = false) {
                val viewModel: OnboardingViewmodel = hiltViewModel()
                val onboardingCompleted by viewModel.onBoardingCompleted.collectAsState(initial = null)
                if (onboardingCompleted != null) {
                    val navController = rememberNavController()
                    val startDestination = if (onboardingCompleted == true) {
                        AppRoutes.HOME
                    } else {
                        OnboardingRoutes.WELCOME
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        OnboardingNavGraph(
                            navController = navController,
                            viewModel = viewModel,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingNavGraph(
    navController: NavHostController,
    viewModel: OnboardingViewmodel,
    startDestination: String
) {
    NavHost(navController, startDestination = startDestination) {
        onBoardingGraph(navController, viewModel)
        homeGraph()
    }
}