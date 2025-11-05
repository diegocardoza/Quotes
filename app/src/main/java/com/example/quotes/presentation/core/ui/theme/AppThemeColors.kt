package com.example.quotes.presentation.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppThemeColors(
    val primary: Color,
    val text: Color,
    val background: Color,
    val onBackground: Color,
    val error: Color
)

val lightThemeColors = AppThemeColors(
    primary = primaryColor,
    text = lightTextColor,
    background = lightBackgroundColor,
    onBackground = lightOnBackgroundColor,
    error = errorColor
)

val darkThemeColors = AppThemeColors(
    primary = primaryColor,
    text = darkTextColor,
    background = darkBackgroundColor,
    onBackground = darkOnBackgroundColor,
    error = errorColor
)

val LocalAppThemeColors = staticCompositionLocalOf<AppThemeColors> {
    error("No theme colors provided")
}