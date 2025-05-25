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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.dropdown.MhDropdownMenu
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.model.Statistics
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.LoadLottieWithModifier
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun HabitsScreen(homeViewmodel: HomeViewmodel) {
    val onboardingViewmodel: OnboardingViewmodel = hiltViewModel()

    val selectedHabit = homeViewmodel.selectedHabit.value
    val habitMap =
        if (selectedHabit.habitId == -1 && homeViewmodel.logs.value.toList().isNotEmpty())
            homeViewmodel.logs.value.toList().first().second
        else
            homeViewmodel.logs.value[selectedHabit.habitId.toString()]


    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F8F1),
                        Color(0xFFC6F5DE)
                    )
                )
            )
            .padding(bottom = 7.dp)
            .padding(horizontal = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        GetHabitsDropdown(homeViewmodel)
        SetVerticalGap(20)
        GetStreakDetails(homeViewmodel)
        SetVerticalGap(20)
        GetHabitTimerCard(homeViewmodel, onboardingViewmodel.userData.value.id)
    }
}

@Composable
fun GetHabitsDropdown(homeViewmodel: HomeViewmodel) {
    val habits = homeViewmodel.habitList.value.map {
        it.habitName
    }
    var selectedIdx by remember { mutableIntStateOf(homeViewmodel.habitList.value.indexOf(homeViewmodel.selectedHabit.value),) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        MhDropdownMenu(
            items = habits,
            selectedIndex = selectedIdx,
            onItemSelected = {
                selectedIdx = it
                homeViewmodel.setSelectedHabit(
                    homeViewmodel.habitList.value[selectedIdx]
                )
                homeViewmodel.loadHabitStatistics(homeViewmodel.habitList.value[selectedIdx].habitId.toString())
            },
            textFieldStyle = MaterialTheme.typography.bodyMedium,
            dropdownItemsStyle = MaterialTheme.typography.bodySmall,
            canShowLabel = false,
            fieldWidth = Integer.MAX_VALUE
        )
    }
}

@Composable
fun GetStreakDetails(homeViewmodel: HomeViewmodel) {

    val selectedHabit = homeViewmodel.selectedHabit.value
    val streakDetails =
        homeViewmodel.habitStatistics.value?.get(selectedHabit.habitId.toString()) ?: Statistics()

    val streakAnim = remember { Animatable(0f) }
    val bestStreak = remember { Animatable(0f) }
    val noOfTimesCompleted = remember { Animatable(0f) }

    LaunchedEffect(homeViewmodel.selectedHabit.value, homeViewmodel.habitStatistics.value) {
        streakAnim.animateTo(
            targetValue = streakDetails.currentStreak.toFloat(),
            animationSpec = tween(durationMillis = 500)
        )
        bestStreak.animateTo(
            targetValue = streakDetails.bestStreak.toFloat(),
            animationSpec = tween(durationMillis = 500)
        )
        noOfTimesCompleted.animateTo(
            targetValue = streakDetails.noOfTimesCompleted.toFloat(),
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
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
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