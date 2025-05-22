package com.cmp.microhabit.ui.screen.onboarding.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.component.text.ConstructText
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitPreferenceTime
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitStoppingReason
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun PersonalizationScreen(onNext: () -> Unit, viewmodel: OnboardingViewmodel = viewModel()) {
    val context = LocalContext.current

    BaseOnboardingScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstructText(
                stringResource(R.string.personalize),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            SetVerticalGap(20)
            ConstructText(
                stringResource(R.string.prefer_habit_time),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            SetVerticalGap(10)
            GetHabitPreferenceButton(viewmodel)
            SetVerticalGap(25)
            ConstructText(
                stringResource(R.string.habit_stopping_reason),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            SetVerticalGap(10)
            GetHabitStoppingReason(viewmodel)
            SetVerticalGap(35)
            MhButton(
                name = stringResource(R.string.continue_text),
                onClick = {
                    if (viewmodel.habitStoppingReason.isNotEmpty()
                        && viewmodel.habitPreferenceTime.value != HabitPreferenceTime.NONE
                    ) {
                        viewmodel.addUserData { isSuccess, res ->
                            if (isSuccess) {
                                viewmodel.setUserId(res ?: -1)
                                onNext()
                            } else {
                                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                fillColor = if (viewmodel.habitStoppingReason.isEmpty()
                    || viewmodel.habitPreferenceTime.value == HabitPreferenceTime.NONE
                )
                    MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary,
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
fun GetHabitPreferenceButton(viewmodel: OnboardingViewmodel) {
    var preferenceTime = remember {
        HabitPreferenceTime.entries.dropLast(1).toList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        preferenceTime.chunked(2).forEach { time ->
            Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                time.forEach {
                    val isSelected = viewmodel.habitPreferenceTime.value == it
                    MhButton(
                        name = stringResource(it.getDisplayNameRes()),
                        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        borderWidth = if (isSelected) 2 else 1,
                        borderRadius = 20,
                        animation = if (isSelected) ButtonAnimation.SPRING else null,
                        textStyle = MaterialTheme.typography.bodySmall,
                        width = 120,
                        fillColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                        enableHapticFeedback = true,
                        paddingHorizontal = 12,
                        paddingVertical = 12,
                    ) {
                        viewmodel.setHabitPreferenceTime(it)
                    }
                }
            }
        }
    }

}


@Composable
fun GetHabitStoppingReason(viewmodel: OnboardingViewmodel) {
    var preferenceTime = remember {
        HabitStoppingReason.entries.dropLast(1).toList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        preferenceTime.chunked(2).forEach { time ->
            Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                time.forEach {
                    val isSelected = viewmodel.habitStoppingReason.contains(it)
                    MhButton(
                        name = stringResource(it.getDisplayNameRes()),
                        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        borderWidth = if (isSelected) 2 else 1,
                        borderRadius = 20,
                        animation = if (isSelected) ButtonAnimation.SPRING else null,
                        textStyle = MaterialTheme.typography.bodySmall,
                        width = 120,
                        fillColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                        enableHapticFeedback = true,
                        paddingHorizontal = 12,
                        paddingVertical = 12,
                    ) {
                        if (viewmodel.habitStoppingReason.contains(it)) {
                            viewmodel.removeHabitStoppingReason(it)
                        } else {
                            viewmodel.addHabitStoppingReason(it)
                        }
                    }
                }
            }
        }
    }

}

fun HabitPreferenceTime.getDisplayNameRes(): Int {
    return when (this) {
        HabitPreferenceTime.MORNING -> R.string.morning
        HabitPreferenceTime.AFTERNOON -> R.string.afternoon
        HabitPreferenceTime.EVENING -> R.string.evening
        HabitPreferenceTime.ANYTIME -> R.string.anytime
        HabitPreferenceTime.NONE -> R.string.none
    }
}

fun HabitStoppingReason.getDisplayNameRes(): Int {
    return when (this) {
        HabitStoppingReason.TIME -> R.string.time
        HabitStoppingReason.FORGETFULNESS -> R.string.forgetfulness
        HabitStoppingReason.MOTIVATION -> R.string.motivation
        HabitStoppingReason.DISTRACTION -> R.string.distraction
        HabitStoppingReason.NONE -> R.string.none
    }
}


@Preview
@Composable
fun PersonalizationPreview() {
    PersonalizationScreen(onNext = {})
}