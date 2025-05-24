package com.cmp.microhabit.ui.screen.habits.screen

import androidx.annotation.RawRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.screen.habits.viewmodel.HabitsViewmodel
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.utils.LoadLottieWithModifier
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.TimeUtils

@Composable
fun HabitsScreen(homeViewmodel: HomeViewmodel) {
    val habitsViewmodel: HabitsViewmodel = hiltViewModel()

    val selectedHabit = homeViewmodel.selectedHabit.value
    val habitMap = if (selectedHabit.id == -1L && homeViewmodel.logs.value.toList().isNotEmpty())
        homeViewmodel.logs.value.toList().first().second
    else
        homeViewmodel.logs.value[selectedHabit.id.toString()]

    val currentHabit = habitMap?.get(TimeUtils.getToday("dd"))

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
        GetStreakDetails(homeViewmodel, currentHabit)
        SetVerticalGap(20)
        GetHabitTimerCard(currentHabit?.spendingMinutes ?: 0)
    }
}


@Composable
fun GetStreakDetails(homeViewmodel: HomeViewmodel, currentHabit: HabitLog?) {


    val streakAnim = remember { Animatable(0f) }
    val bestStreak = remember { Animatable(0f) }
    val noOfTimesCompleted = remember { Animatable(0f) }

    LaunchedEffect(homeViewmodel.selectedHabit.value) {
        streakAnim.animateTo(
            targetValue = currentHabit?.streak?.toFloat() ?: 0f,
            animationSpec = tween(durationMillis = 500)
        )
        bestStreak.animateTo(
            targetValue = currentHabit?.bestStreak?.toFloat() ?: 0f,
            animationSpec = tween(durationMillis = 500)
        )
        noOfTimesCompleted.animateTo(
            targetValue = currentHabit?.noOfTimesCompleted?.toFloat() ?: 0f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Column {
        ConstructStreakCard(
            value = streakAnim.value.toInt().toString(),
            title = " Days Current Streak",
            resId = R.raw.fire_lottie,
            size = 20,
            iconPadding = 15
        )
        ConstructStreakCard(
            value = bestStreak.value.toInt().toString(),
            title = " Days Best Streak",
            resId = R.raw.trophy_lottie,
            size = 35,
            iconPadding = 10

        )
        ConstructStreakCard(
            value = noOfTimesCompleted.value.toInt().toString(),
            title = " Times Completed",
            resId = R.raw.abacus_lottie,
            size = 30,
            iconPadding = 10
        )
    }
}

@Composable
fun ConstructStreakCard(
    value: String,
    title: String,
    @RawRes resId: Int,
    size: Int,
    iconPadding: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadLottieWithModifier(
                resId,
                modifier = Modifier
                    .padding(vertical = iconPadding.dp)
                    .size(size.dp)
            )
            Text(
                value,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}