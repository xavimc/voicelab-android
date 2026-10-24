package io.github.xavimc.voicelab.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryDark,
    onPrimary = TealOnPrimaryDark,
    primaryContainer = TealPrimaryContainerDark,
    onPrimaryContainer = TealOnPrimaryContainerDark,
    secondary = TealSecondaryDark,
    onSecondary = TealOnSecondaryDark,
    secondaryContainer = TealSecondaryContainerDark,
    onSecondaryContainer = TealOnSecondaryContainerDark,
    tertiary = TealTertiaryDark,
    onTertiary = TealOnTertiaryDark,
    tertiaryContainer = TealTertiaryContainerDark,
    onTertiaryContainer = TealOnTertiaryContainerDark,
    background = TealSurfaceDark,
    onBackground = TealOnSurfaceDark,
    surface = TealSurfaceDark,
    onSurface = TealOnSurfaceDark,
    surfaceVariant = TealSurfaceVariantDark,
    onSurfaceVariant = TealOnSurfaceVariantDark,
    surfaceContainer = TealSurfaceContainerDark,
    outline = TealOutlineDark,
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimaryLight,
    onPrimary = TealOnPrimaryLight,
    primaryContainer = TealPrimaryContainerLight,
    onPrimaryContainer = TealOnPrimaryContainerLight,
    secondary = TealSecondaryLight,
    onSecondary = TealOnSecondaryLight,
    secondaryContainer = TealSecondaryContainerLight,
    onSecondaryContainer = TealOnSecondaryContainerLight,
    tertiary = TealTertiaryLight,
    onTertiary = TealOnTertiaryLight,
    tertiaryContainer = TealTertiaryContainerLight,
    onTertiaryContainer = TealOnTertiaryContainerLight,
    background = TealSurfaceLight,
    onBackground = TealOnSurfaceLight,
    surface = TealSurfaceLight,
    onSurface = TealOnSurfaceLight,
    surfaceVariant = TealSurfaceVariantLight,
    onSurfaceVariant = TealOnSurfaceVariantLight,
    surfaceContainer = TealSurfaceContainerLight,
    outline = TealOutlineLight,
)

@Composable
fun VoiceLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Off by default so the app looks the same on every device (docs/adr/0004-fixed-color-scheme.md).
    // Dynamic color is available on Android 12+.
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
        content = content
    )
}