package com.cmp.microhabit.ui.screen.home.screens

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.LottieAnimationView
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun HabitGarden(viewmodel: HomeViewmodel) {
    Column {
        Text("Habit Garden", style = MaterialTheme.typography.bodyMedium)
        SetVerticalGap(16)
        GetHabitGarden()
    }
}

@Composable
fun GetHabitGarden() {
    val onBoardingViewmodel: OnboardingViewmodel = hiltViewModel()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        onBoardingViewmodel.focusSelection.chunked(2).forEach {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                it.forEach {
                    GetHabitGardenItem(
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp),
                        it
                    )
                }
            }
        }
    }
}

@Composable
fun GetHabitGardenItem(modifier: Modifier, item: HabitSelection) {
    val haptic = LocalView.current

    Card(
        modifier = modifier.clickable(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(7f)) {
                LottieAnimationView(R.raw.workout_lottie, height = 100)
            }
            Text(
                item.name.drop(3),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
    }
}