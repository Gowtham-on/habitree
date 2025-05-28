package com.cmp.microhabit.ui.screen.habits.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.ShowLottieWithIterations

@Composable
fun GetHabitOverviewCard(homeViewmodel: HomeViewmodel) {
    val selectedHabit by homeViewmodel.selectedHabit

    val treeSize = remember(selectedHabit, homeViewmodel.habitStatistics.value) {
        val count = homeViewmodel.habitStatistics.value?.get(selectedHabit.habitId.toString())?.noOfTimesCompleted ?: 0
        if (count >= 30)
            R.raw.plant_size_l
        else if (count >= 15)
            R.raw.plant_size_m
        else if (count >= 5)
            R.raw.plant_size_s
        else if (count >= 0)
            R.raw.plant_size_xs
        else
            R.raw.plant_size_xs
    }

    val treeHeight = remember(treeSize) {
        when (treeSize) {
            R.raw.plant_size_l -> 300
            R.raw.plant_size_m -> 300
            R.raw.plant_size_s -> 200
            R.raw.plant_size_xs -> 200
            else -> 200
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        key(treeSize) {
            ShowLottieWithIterations(treeSize, iterations = 1, height = treeHeight)
        }
        SetVerticalGap(15)
    }
}