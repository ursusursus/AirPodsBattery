package sk.ursus.airpodsbattery.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

@Composable
fun ScreenRoot(
    statusBarColor: Color = Color.Yellow,
    navigationBarColor: Color = Color.Red,
    backgroundColor: Color = Color.Magenta,
    content: @Composable BoxScope.() -> Unit
) {
    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current).toFloat()
    val navigationBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current).toFloat()
    Log.d("Default", "statusbarheight=$statusBarHeight nav=$navigationBarHeight")

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(backgroundColor)
            .drawBehind {
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