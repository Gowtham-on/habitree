package com.cmp.microhabit.ui.component.calendar

import android.os.Build
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@Composable
fun GetHabitCalendarView(
    viewmodel: HomeViewmodel,
    userId: String
) {

    val logs by viewmodel.logs

    val days = getDateRangeLastSundayToThisSaturday()

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

    LaunchedEffect(viewmodel.selectedHabit.value) {
        viewmodel.loadLogsFromLastWeek(userId, viewmodel.selectedHabit.value.id.toString())
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
                getCurrentMonthYear(),
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
                        GetCircularBg(it, logs)
                        SetVerticalGap(12)
                        GetCircularBg((it.toInt() + 7).toString(), logs)
                        SetVerticalGap(10)
                    }
                }

            }
            SetVerticalGap(6)

        }

    }
}

fun getDateRangeLastSundayToThisSaturday(): List<String> {
    val cal = Calendar.getInstance()
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

    val lastSundayCal = cal.clone() as Calendar
    lastSundayCal.add(Calendar.DAY_OF_YEAR, -13)

    val sdf = SimpleDateFormat("dd", Locale.getDefault())
    val result = mutableListOf<String>()

    val tempCal = lastSundayCal.clone() as Calendar
    while (!tempCal.after(cal)) {
        result.add(sdf.format(tempCal.time))
        tempCal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return result
}

@Composable
fun GetDayText(day: String) {
    Text(day, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun GetCircularBg(date: String, logs: Map<String, HabitLog>) {
    val log = if (logs.contains(date)) {
        logs[date]
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
                if (isToday(date)) {
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
                        color = if (log!!.completed) MaterialTheme.colorScheme.primary
                        else if (isToday(date)) Color.Red
                        else Color.Gray,
                        shape = CircleShape
                    )
                }
            )
    ) {
        Text(
            date,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        )
    }
}

fun isToday(dateString: String): Boolean {
    val today = Calendar.getInstance()
    return today.get(Calendar.DAY_OF_MONTH) == dateString.toInt()
}

fun getCurrentMonthYear(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter =
            java.time.format.DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault())
        val currentDate = LocalDate.now()
        currentDate.format(formatter)
    } else {
        val sdf = java.text.SimpleDateFormat("MMM yyyy", Locale.getDefault())
        val currentDate = java.util.Calendar.getInstance().time
        sdf.format(currentDate)
    }
}
