package com.cmp.microhabit.ui.component.calendar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.TimeUtils

@Composable
fun GetHabitCalendarView(
    viewmodel: HomeViewmodel,
) {


    val days = TimeUtils.getDateRangeLastSundayToThisSaturday(TimeUtils.getDefaultPattern(), 13)

    val daysList = remember {
        listOf(
            "Su",
            "Mo",
            "Tu",
            "We",
            "Th",
            "Fr",
            "Sa",
            "Su",
            "Mo",
            "Tu",
            "We",
            "Th",
            "Fr",
            "Sa"
        )
    }

    val logs by viewmodel.logs
    val habitLog = logs["${viewmodel.getSelectedHabitId()}"]

    LaunchedEffect(viewmodel.selectedHabit.value) {
        viewmodel.loadLogsForHabit(viewmodel.selectedHabit.value.habitId.toString())
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f)
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                TimeUtils.getCurrentMonthYear(),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                days.take(7).mapIndexed { index, it ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GetDayText(daysList[index])
                        SetVerticalGap(12)
                        GetCircularBg(it, habitLog?.dateLogs ?: mapOf())
                        SetVerticalGap(12)
                        GetCircularBg(
                            "${it.take(2).toInt() + 7}" + it.drop(2),
                            habitLog?.dateLogs ?: mapOf()
                        )
                        SetVerticalGap(10)
                    }
                }
            }
            SetVerticalGap(6)

        }

    }
}

@Composable
fun GetDayText(day: String) {
    Text(day, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun GetCircularBg(date: String, dateLogs: Map<String, Boolean>) {
    val isCompleted = if (dateLogs.contains(date) == true) {
        dateLogs[date]
    } else {
        HabitLog()
    }

    val infiniteTransition = rememberInfiniteTransition()
    val alphaValue by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(35.dp)
            .then(
                if (TimeUtils.isToday(date.take(2))) {
                    Modifier
                        .border(
                            width = 3.dp,
                            color = Color(0xFFF4B65C).copy(alpha = alphaValue),
                            shape = CircleShape
                        )
                        .padding(all = 3.dp)
                        .background(
                            shape = CircleShape,
                            color = Color(0xFFF4B65C)
                        )

                } else {
                    Modifier.background(
                        color = if (isCompleted == true) MaterialTheme.colorScheme.primary
                        else if (TimeUtils.isToday(date.take(2))) Color.Red
                        else Color.Gray,
                        shape = CircleShape
                    )
                }
            )
    ) {
        Text(
            date.take(2),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        )
    }
}

