package com.example.quotes.presentation.core.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotes.presentation.core.ui.theme.LocalAppThemeColors

@Composable
fun ErrorMessageLayout(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    val appThemeColors = LocalAppThemeColors.current
    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = appThemeColors.error
        )
    }
}