package com.cmp.microhabit.ui.screen.habits.screen

import TimerViewModel
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.ShowLottieWithIterations
import com.cmp.microhabit.utils.TimeUtils

@Composable
fun GetHabitTimerCard(homeViewmodel: HomeViewmodel, isHabitCompleted: Boolean) {
    val haptic = LocalView.current

    val countdownViewModel =
        remember {
            TimerViewModel(
                initialSeconds = homeViewmodel.selectedHabit.value.timeForHabit.toInt().times(60)
            )
        }

    LaunchedEffect(homeViewmodel.selectedHabit.value) {
        countdownViewModel.stop()
        countdownViewModel.secondsLeft =
            homeViewmodel.selectedHabit.value.timeForHabit.toInt().times(60)
    }

    val seconds = countdownViewModel.secondsLeft

    val formatted = String.format(
        stringResource(R.string.timer_min_sec),
        seconds / 60,
        seconds % 60
    )

    if (!isHabitCompleted)
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier.padding(4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ShowLottieWithIterations(
                        animationRes = R.raw.timer_lottie,
                        iterations = 30,
                        play = true,
                        height = 35,
                        speed = if (!countdownViewModel.isRunning()) 0.002f else 0.2f,
                    )
                    Text(
                        formatted,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }

                SetVerticalGap(20)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TimerButtons(stringResource(R.string.start)) {
                        countdownViewModel.start {
                            homeViewmodel.completeTodayTask(
                                homeViewmodel.getSelectedHabitId().toString(),
                                TimeUtils.getToday(),
                                true
                            )
                        }
                    }
                    TimerButtons(stringResource(R.string.pause)) {
                        countdownViewModel.pause()
                    }
                    TimerButtons(stringResource(R.string.reset)) {
                        countdownViewModel.stop()
                    }
                }
                SetVerticalGap(18)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.already_completed),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        stringResource(R.string.mark_as_done),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = {
                            haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                            homeViewmodel.completeTodayTask(
                                homeViewmodel.getSelectedHabitId().toString(),
                                TimeUtils.getToday(),
                                true
                            )
                        })
                    )
                }
            }
        }
    else
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier.padding(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        "🎉 You completed this habit today!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "\uD83C\uDF3F Your plant grew a little leaf.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "\uD83D\uDD25 +1 streak added!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
}


@Composable
fun TimerButtons(text: String, onClick: () -> Unit) {
    MhButton(
        name = text,
        onClick = onClick,
        fillColor = MaterialTheme.colorScheme.primary,
        borderRadius = 50,
        textColor = Color.White,
        paddingHorizontal = 22,
        paddingVertical = 12,
        textStyle = MaterialTheme.typography.bodyMedium,
        animationIncreaseSize = 1.07f,
        animation = ButtonAnimation.SPRING,
        enableHapticFeedback = true,
    )
}