package sk.ursus.airpodsbattery.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sk.ursus.airpodsbattery.R
import sk.ursus.airpodsbattery.checker.AirPodsBatteryChecker
import sk.ursus.airpodsbattery.checker.AirPodsBatteryCheckerState
import sk.ursus.airpodsbattery.checker.AirPodsBatteryLevel

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(batteryChecker: AirPodsBatteryChecker) {
    ScreenRoot {
        val state by remember { batteryChecker.airPodsBatteryLevel() }
            .collectAsStateWithLifecycle(initialValue = AirPodsBatteryCheckerState.Connecting)

//        val scrollState = rememberScrollState()
//        when (val state = state) {
//            AirPodsBatteryCheckerState.Connecting -> Body(null, scrollState)
//            is AirPodsBatteryCheckerState.Connected -> Body(state.airPodsBatteryLevel, scrollState)
//            is AirPodsBatteryCheckerState.Error -> Body(AirPodsBatteryLevel(-1, -1, -1), scrollState)
//        }
        Body2()
    }
}

@Composable
private fun Body(batteryLevel: AirPodsBatteryLevel?, scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        SmallCard(batteryLevel = batteryLevel)
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightOfStatusBar()
                .background(Color.Yellow)
        )
        DynamicColorPalette()
    }
//    NavigationBar() {
//
//    }
}

@Composable
private fun Body2() {
    LazyColumn(
        contentPadding = WindowInsets.navigationBars.asPaddingValues() + PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
        item { NameAndColor(name = "Foo", color = Color.Magenta) }
    }
}

fun Modifier.heightOfStatusBar(): Modifier = composed {
    windowInsetsTopHeight(WindowInsets.systemBars)
}

@Composable
fun SmallCard(batteryLevel: AirPodsBatteryLevel?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .drawBehind {
                }
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_headphones),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                text = "Ursus AirPods Pro 2",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ValueColumn(label = "L", value = batteryLevel?.left)
                ValueColumn(label = "R", value = batteryLevel?.right)
                ValueColumn(label = "C", value = batteryLevel?.case)
            }
        }
    }
}

@Composable
private fun ValueColumn(label: String, value: Int?) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        if (value == null) {
            PercentValue(value = formatValue(0), transparent = true)
        } else {
            PercentValue(value = formatValue(value), transparent = false)
        }
    }
}

@Composable
private fun formatValue(value: Int): String {
    return if (value < 10) {
        "0$value"
    } else {
        value.toString()
    }
}

@Composable
private fun PercentValue(value: String, transparent: Boolean) {
    Row(modifier = if (transparent) Modifier.alpha(0.3f) else Modifier) {
        Text(
            modifier = Modifier
                .alignByBaseline(),
            style = MaterialTheme.typography.headlineSmall,
            text = value,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            modifier = Modifier
                .alignByBaseline(),
            style = MaterialTheme.typography.titleMedium,
            text = "%",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun crossFade(durationMillis: Int = 1000): ContentTransform {
    return ContentTransform(
        targetContentEnter = fadeIn(animationSpec = tween(durationMillis)),
        initialContentExit = fadeOut(animationSpec = tween(durationMillis))
    )
}

@Composable
fun DynamicColorPalette() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NameAndColor("Primary", MaterialTheme.colorScheme.primary)
        NameAndColor("On primary", MaterialTheme.colorScheme.onPrimary)
        NameAndColor("Primary container", MaterialTheme.colorScheme.primaryContainer)
        NameAndColor("On primary container", MaterialTheme.colorScheme.onPrimaryContainer)
        NameAndColor("Inverse primary", MaterialTheme.colorScheme.inversePrimary)

        Spacer(modifier = Modifier.height(8.dp))

        NameAndColor("Secondary", MaterialTheme.colorScheme.secondary)
        NameAndColor("On secondary", MaterialTheme.colorScheme.onSecondary)
        NameAndColor("Secondary container", MaterialTheme.colorScheme.secondaryContainer)
        NameAndColor("On secondary container", MaterialTheme.colorScheme.onSecondaryContainer)

        Spacer(modifier = Modifier.height(8.dp))

        NameAndColor("Tertiary", MaterialTheme.colorScheme.tertiary)
        NameAndColor("On tertiary", MaterialTheme.colorScheme.onTertiary)
        NameAndColor("Tertiary container", MaterialTheme.colorScheme.tertiaryContainer)
        NameAndColor("On tertiary container", MaterialTheme.colorScheme.onTertiaryContainer)

        Spacer(modifier = Modifier.height(8.dp))

        NameAndColor("Surface", MaterialTheme.colorScheme.surface)
        NameAndColor("On surface", MaterialTheme.colorScheme.onSurface)
        NameAndColor("Surface variant", MaterialTheme.colorScheme.surfaceVariant)
        NameAndColor("On surface variant", MaterialTheme.colorScheme.onSurfaceVariant)
        NameAndColor("Inverse surface", MaterialTheme.colorScheme.inverseSurface)
        NameAndColor("On inverse surface", MaterialTheme.colorScheme.inverseOnSurface)

        Spacer(modifier = Modifier.height(8.dp))

        NameAndColor("Background", MaterialTheme.colorScheme.background)
        NameAndColor("On background", MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun NameAndColor(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f), MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}
