package sk.ursus.airpodsbattery.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
fun ScreenRoot(
    statusBarColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    navigationBarColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    backgroundColor: Color = Color.Magenta,
    insetBottom: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val insets = WindowInsets.systemBars
    val statusBarHeight = insets.getTop(density).toFloat()
    val navigationBarHeight = insets.getBottom(density).toFloat()
    Log.d("Default", "statusbarheight=$statusBarHeight nav=$navigationBarHeight l=${insets.getLeft(density, layoutDirection).toFloat()} r=${insets.getRight(density, layoutDirection).toFloat()}")

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(backgroundColor)
            .drawBehind {  }
            .drawWithContent {
                drawContent()
                Log.d("Default", "draw")
                drawRect(
                    color = statusBarColor,
                    size = Size(width = size.width, height = statusBarHeight)
                )
                drawRect(
                    color = navigationBarColor,
                    topLeft = Offset(0f, size.height - navigationBarHeight),
                    size = Size(width = size.width, height = navigationBarHeight)
                )
            }
            .statusBarsPadding(),
        content = content
    )
}