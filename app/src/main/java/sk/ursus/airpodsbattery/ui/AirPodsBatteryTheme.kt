package sk.ursus.airpodsbattery.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import sk.ursus.airpodsbattery.R

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Magenta = Color(0xFFAF4BF0)
val Magenta_10 = Color(0xFFC28CE6)

private val colorScheme = lightColorScheme(
    primary = Magenta,
    primaryContainer = Magenta_10,
    onPrimary = White,
    onPrimaryContainer = Magenta
)

private val poppinsFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Normal
    )
)

private val platformStyle = PlatformTextStyle(
    includeFontPadding = false
)

private val typography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        letterSpacing = -(1.0).sp,
        platformStyle = platformStyle,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = -(1.0).sp,
        platformStyle = platformStyle,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = -(1.0).sp,
        platformStyle = platformStyle,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        platformStyle = platformStyle,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        platformStyle = platformStyle,
        fontSize = 14.sp
    )
)

@Composable
fun AirPodsBatterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}