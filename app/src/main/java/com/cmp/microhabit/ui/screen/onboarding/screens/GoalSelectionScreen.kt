package com.cmp.microhabit.ui.screen.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.component.text.ConstructText
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.ui.screen.onboarding.viewmodel.OnboardingViewmodel
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun GoalSelectionScreen(viewmodel: OnboardingViewmodel, onNext: () -> Unit) {
    BaseOnboardingScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ConstructText(
                stringResource(R.string.main_focus),
                style = MaterialTheme.typography.titleLarge,
            )
            SetVerticalGap(60)
            GetButtonOptions(viewmodel)
            SetVerticalGap(20)
            MhButton(
                name = stringResource(R.string.continue_text),
                onClick = {
                    if (viewmodel.focusSelection.isNotEmpty()) {
                        onNext()
                    }
                },
                fillColor = if (viewmodel.focusSelection.isEmpty()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
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
fun GetButtonOptions(viewmodel: OnboardingViewmodel) {
    val context = LocalContext.current
    val buttonData = remember {
        listOf(
            HabitSelection(1, context.getString(R.string.mindfulness), 0),
            HabitSelection(2, context.getString(R.string.fitness_health), 0),
            HabitSelection(3, context.getString(R.string.learning_productivity), 0),
            HabitSelection(4, context.getString(R.string.better_sleep), 0),
            HabitSelection(5, context.getString(R.string.creative_habits), 0),
            HabitSelection(6, context.getString(R.string.career_growth), 0),
        )
    }

    buttonData.forEach {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            val isSelected = viewmodel.focusSelection.contains(it)
            MhButton(
                name = it.name,
                borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                borderWidth = if (isSelected) 2 else 1,
                borderRadius = 20,
                animation = if (isSelected) ButtonAnimation.SPRING else null,
                textStyle = MaterialTheme.typography.bodyMedium,
                fillMaxWidth = true,
                textAlignment = TextAlign.Start,
                paddingVertical = 15,
                animationIncreaseSize = 1.07f,
                enableHapticFeedback = true,
                fillColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                canShowShadow = true,
                paddingHorizontal = 13,
            ) {
                if (isSelected) {
                    viewmodel.removeFocusSelection(it)
                } else {
                    viewmodel.addFocusSelection(it)
                }
            }
            SetVerticalGap(20)
        }
    }

}