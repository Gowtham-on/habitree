package com.cmp.microhabit.ui.screen.habits.screen

import TimerViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.utils.SetVerticalGap
import com.cmp.microhabit.utils.ShowLottieWithIterations

@Composable
fun GetHabitTimerCard(spendingMinutes: Long) {
    val countdownViewModel =
        remember { TimerViewModel(initialSeconds = spendingMinutes.toInt() * 60) }

    val seconds = countdownViewModel.secondsLeft

    val formatted = String.format(
        stringResource(R.string.timer_min_sec),
        seconds / 60,
        seconds % 60
    )

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
                    countdownViewModel.start()
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
                    fontWeight = FontWeight.Bold
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