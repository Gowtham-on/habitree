package com.cmp.microhabit.ui.screen.home.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.calendar.GetHabitCalendarView
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.LottieAnimationView
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun HabitGarden(
    viewmodel: HomeViewmodel,
    onboardingViewmodel: OnboardingViewmodel,
    userId: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            "Habit Garden",
            style = MaterialTheme.typography.bodyMedium,
        )
        SetVerticalGap(16)
        GetHabitGarden(onboardingViewmodel, homeViewmodel = viewmodel)
        SetVerticalGap(16)
        GetStartButton(viewmodel, navController)
        SetVerticalGap(16)
        GetHabitCalendarView(viewmodel, userId)
        SetVerticalGap(20)

    }
}

@Composable
fun GetHabitGarden(onBoardingViewmodel: OnboardingViewmodel, homeViewmodel: HomeViewmodel) {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        onBoardingViewmodel.userData.value.habitPreference.chunked(2).forEach {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                it.forEach {
                    GetHabitGardenItem(
                        modifier = Modifier
                            .weight(1f),
                        it,
                        homeViewmodel = homeViewmodel
                    )
                }
            }
        }
    }
}

@Composable
fun GetHabitGardenItem(modifier: Modifier, item: HabitSelection, homeViewmodel: HomeViewmodel) {
    val haptic = LocalView.current

    Card(
        modifier = modifier
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    homeViewmodel.setSelectedHabit(item)
                }
            )
            .then(
                if (item.id == homeViewmodel.selectedHabit.value.id)
                    modifier
                        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (item.id == homeViewmodel.selectedHabit.value.id)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                Color.White.copy(alpha = 0.8f)
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
                item.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
    }
}

@Composable
fun GetStartButton(viewmodel: HomeViewmodel, navController: NavHostController) {

    val haptic = LocalView.current

    val infiniteTransition = rememberInfiniteTransition()
    val borderWidth by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .border(
                width = borderWidth.toInt().dp,
                color = Color(0xFFC6F5DE),
                shape = RoundedCornerShape(35.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(35.dp)
            )
            .padding(7.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable(onClick = {
                    haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    navController.navigate("Habits") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }),
        ) {
            Text(
                stringResource(
                    R.string.quick_start,
                    viewmodel.selectedHabit.value.name
                ),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 18.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}