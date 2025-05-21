package com.cmp.microhabit.ui.component.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class HabitCalendarView(
    val day: String,
    val month: String,
    val year: String,
    val isHabitCompleted: Boolean = false,
    val isToday: Boolean,
    val dayStartTime: Long? = null,
    val dayEndTime: Long? = null,
)

@Composable
fun GetHabitCalendarView() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f)
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Text(
                getCurrentMonthYear(),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        } else {
            Text(
                "This Month", modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentMonthYear(): String {
    val formatter = java.time.format.DateTimeFormatter.ofPattern("MMM yyyy")
    val currentDate = java.time.LocalDate.now()
    return currentDate.format(formatter)
}


@Preview(showBackground = false)
@Composable
fun PreviewCalendar() {
    GetHabitCalendarView()
}