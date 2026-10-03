package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NexaColorScheme = darkColorScheme(
    primary = NexaCyanAccent,
    onPrimary = NexaMidnight,
    primaryContainer = NexaElectricBlue,
    onPrimaryContainer = NexaChrome,
    secondary = NexaSilver,
    onSecondary = NexaMidnight,
    secondaryContainer = NexaCardSurface,
    onSecondaryContainer = NexaChrome,
    tertiary = NexaGold,
    onTertiary = NexaMidnight,
    background = NexaMidnight,
    onBackground = NexaChrome,
    surface = NexaNavy,
    onSurface = NexaChrome,
    surfaceVariant = NexaCardSurface,
    onSurfaceVariant = NexaSilver,
    outline = NexaCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Automotive luxury dark theme by default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NexaColorScheme,
        typography = Typography,
        content = content
    )
}
