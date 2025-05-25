package com.cmp.microhabit.ui.component.dropdown

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MhDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    label: String = "Choose",
    textFieldStyle: TextStyle,
    dropdownItemsStyle: TextStyle,
    canShowLabel: Boolean = false,
    fieldWidth: Int = 100,
    maxLines: Int = 1
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = items.getOrNull(selectedIndex) ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(fieldWidth.dp),
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    "Select habit",
                    style = textFieldStyle,
                )
            },
            label = if (canShowLabel) ({
                Text(
                    label,
                    style = textFieldStyle,
                    maxLines = 1,
                )
            }) else null,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .clip(RoundedCornerShape(20.dp))
                .width(fieldWidth.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = textFieldStyle,
            maxLines = maxLines,
            singleLine = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White

        ) {
            items.forEachIndexed { idx, item ->
                DropdownMenuItem(
                    text = { Text(item, style = dropdownItemsStyle) },
                    onClick = {
                        onItemSelected(idx)
                        expanded = false
                    }
                )
            }
        }
    }
}
