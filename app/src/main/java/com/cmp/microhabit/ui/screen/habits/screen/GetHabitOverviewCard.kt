package com.cmp.microhabit.ui.screen.habits.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel

@Composable
fun GetHabitOverviewCard(homeViewmodel: HomeViewmodel) {
    val selectedHabit by homeViewmodel.selectedHabit

    if (selectedHabit.habitId != -1)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(selectedHabit.habitName, style = MaterialTheme.typography.bodyLarge)

            }
        }
}