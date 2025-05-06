package com.cmp.microhabit.ui.component.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ConstructText(
    text: String,
    style: TextStyle? = null,
    color: Color? = null,
    textAlign: TextAlign? = null
) {
    Text(
        text,
        style = style ?: LocalTextStyle.current,
        color = color ?: Color.Unspecified,
        textAlign = textAlign
    )
}