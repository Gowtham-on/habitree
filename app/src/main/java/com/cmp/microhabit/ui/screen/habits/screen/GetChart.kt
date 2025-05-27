package com.cmp.microhabit.ui.screen.habits.screen

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.dropdown.MhDropdownMenu
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.ShowLottieWithIterations
import com.cmp.microhabit.utils.TimeUtils.getCurrentMonthDatesInFormat
import com.cmp.microhabit.utils.TimeUtils.getCurrentWeekDatesInFormat
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.LineChartData.Point
import me.bytebeats.views.charts.line.render.line.GradientLineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer

@Composable
fun GetHabitLineChart(homeViewmodel: HomeViewmodel) {
    val monthDates = getCurrentMonthDatesInFormat("dd-MM-yyyy")
    val weekDates = getCurrentWeekDatesInFormat("dd-MM-yyyy")

    val chartData by homeViewmodel.chartData
    val selectedHabit by homeViewmodel.selectedHabit

    var selectedIdx by remember {
        mutableIntStateOf(0)
    }

    var hasValue by remember { mutableStateOf(false) }

    val points = remember(chartData, selectedHabit, selectedIdx) {
        hasValue = false
        if (selectedIdx == 0) {
            weekDates.map { date ->
                var first =
                    chartData[selectedHabit.habitId.toString()]?.streaks[date]
                var second = date.take(2)

                if (first == null) {
                    first = 0
                } else {
                    hasValue = true
                    first.toString().take(2).toFloat()
                }
                Point(first.toFloat(), second)
            }
        } else {
            monthDates.map { date ->
                var first =
                    chartData[selectedHabit.habitId.toString()]?.streaks[date]
                var second = date.take(2)

                if (first == null) {
                    first = 0
                } else {
                    hasValue = true
                    first.toString().take(2).toFloat()
                }
                Point(first.toFloat(), second)
            }
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.padding(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SetVerticalGap(20)
            Text("Habit Streak Progress", style = MaterialTheme.typography.titleMedium)
            SetVerticalGap(20)
            Box(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                MhDropdownMenu(
                    items = listOf("Weekly View", "Monthly View"),
                    selectedIndex = selectedIdx,
                    onItemSelected = {
                        selectedIdx = it
                    },
                    textFieldStyle = MaterialTheme.typography.bodyMedium,
                    dropdownItemsStyle = MaterialTheme.typography.bodySmall,
                    canShowLabel = false,
                    dropDownModifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp),
                        )
                        .fillMaxWidth(),
                    fieldColor = MaterialTheme.colorScheme.secondary
                )
            }
            if (hasValue)
                Column {
                    SetVerticalGap(10)
                    LineChart(
                        lineChartData = LineChartData(
                            startAtZero = true,
                            points = points
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(vertical = 20.dp)
                            .padding(top = 10.dp, end = 10.dp),
                        animation = TweenSpec(durationMillis = 2500),
                        pointDrawer = FilledCircularPointDrawer(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        lineShader = GradientLineShader(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                MaterialTheme.colorScheme.secondary,
                            )
                        ),
                        lineDrawer = SolidLineDrawer(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 2.dp
                        ),
                        xAxisDrawer = SimpleXAxisDrawer(
                            drawLabelEvery = if (selectedIdx == 1) 3 else 1,
                        ),
                        yAxisDrawer = SimpleYAxisDrawer(
                            labelTextSize = 14.sp,
                            labelValueFormatter = { value ->
                                value.toInt().toString()
                            },
                            drawLabelEvery = 3
                        ),
                        horizontalOffset = 0f,
                    )
                }
            else
                Column {
                    ShowLottieWithIterations(
                        R.raw.line_chart_lottie_2,
                        iterations = 1,
                        height = 200
                    )
                    Text("No Data Available", style = MaterialTheme.typography.titleMedium)
                    SetVerticalGap(20)
                }
        }

    }

}