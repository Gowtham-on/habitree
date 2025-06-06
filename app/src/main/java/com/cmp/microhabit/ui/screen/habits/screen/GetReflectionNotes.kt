package com.cmp.microhabit.ui.screen.habits.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.TimeUtils


@Composable
fun GetReflectionNotes(homeViewmodel: HomeViewmodel) {
    var notes by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Text(
            stringResource(R.string.reflection_notes),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(15.dp)
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            onValueChange = { notes = it },
            value = notes,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            maxLines = 4,
            shape = RoundedCornerShape(15.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    stringResource(R.string.write_about_today_s_session),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
        MhButton(
            name = stringResource(R.string.save),
            fillColor = if (notes.isNotEmpty()) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 15.dp),
            textColor = Color.White,
            paddingHorizontal = 22,
            paddingVertical = 12,
            borderRadius = 58,
            textStyle = MaterialTheme.typography.bodyMedium
        ) {
            if (notes.isNotEmpty()) {
                homeViewmodel.addReflectionNotes(habitId = "${homeViewmodel.getSelectedHabitId()}", date = TimeUtils.getToday(), notes = notes) {
                    notes = ""
                    Toast.makeText(context,
                        context.getString(R.string.notes_added_successfully), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}