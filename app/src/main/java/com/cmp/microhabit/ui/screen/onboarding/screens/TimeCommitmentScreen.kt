package com.cmp.microhabit.ui.screen.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.component.text.ConstructText
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.LottieAnimationView
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun TimeCommitmentScreen(viewmodel: OnboardingViewmodel, onNext: () -> Unit) {

    BaseOnboardingScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ConstructText(
                stringResource(R.string.time_commitment),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            LottieAnimationView(R.raw.hour_glass_lottie)
            SetVerticalGap(20)
            GetTimeGroupButtons(viewmodel)
            SetVerticalGap(10)
            ConstructText(
                stringResource(R.string.adjust_later),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            SetVerticalGap(35)
            MhButton(
                name = stringResource(R.string.continue_text),
                onClick = {
                    if (viewmodel.timeSelection.value != -1) {
                        onNext()
                    }
                },
                fillColor = if (viewmodel.timeSelection.value == -1) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                textColor = Color.White,
                borderRadius = 50,
                padding = 12,
                animation = ButtonAnimation.SPRING,
                textStyle = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.W700,
            )
        }
    }
}

@Composable
fun GetTimeGroupButtons(viewmodel: OnboardingViewmodel) {
    var time = remember {
        listOf(1, 2, 3, 4, 5)
    }

    time.chunked(2).forEach {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                it.forEach { min ->
                    var isSelected = viewmodel.timeSelection.value == min
                    Row {
                        MhButton(
                            name = "$min min",
                            borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                            borderWidth = if (isSelected) 2 else 1,
                            borderRadius = 20,
                            animation = if (isSelected) ButtonAnimation.SPRING else null,
                            textStyle = MaterialTheme.typography.bodyMedium,
                            width = 100,
                            fillColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                            enableHapticFeedback = true,
                            paddingHorizontal = 12,
                            paddingVertical = 12,
                            textAlignment = TextAlign.Center
                        ) {
                            viewmodel.addTimeSelection(min)
                        }
                    }
                }
            }
            SetVerticalGap(18)
        }
    }
}