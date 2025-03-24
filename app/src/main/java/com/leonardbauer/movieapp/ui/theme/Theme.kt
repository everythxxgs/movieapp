package com.leonardbauer.movieapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val whiteColor = androidx.compose.ui.graphics.Color.White

private val DarkColorScheme = darkColorScheme(
    primary = IOSDarkPurple,
    secondary = PurplePrimary,
    tertiary = IOSBlue,
    background = IOSDarkBackground,
    surface = IOSDarkSurface,
    onPrimary = whiteColor,
    onSecondary = whiteColor,
    onTertiary = whiteColor,
    onBackground = whiteColor,
    onSurface = whiteColor
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleDark,
    tertiary = IOSBlue,
    background = IOSLightGray,
    surface = whiteColor,
    onPrimary = whiteColor,
    onSecondary = whiteColor,
    onTertiary = whiteColor,
    onBackground = IOSTextPrimary,
    onSurface = IOSTextPrimary
)

@Composable
fun MovieappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // iOS design doesn't use dynamic colors like Material, so set to false by default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}