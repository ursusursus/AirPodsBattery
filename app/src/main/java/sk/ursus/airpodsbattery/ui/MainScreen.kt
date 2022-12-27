package sk.ursus.airpodsbattery.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
    val state by remember { batteryChecker.airPodsBatteryLevel() }
        .collectAsStateWithLifecycle(initialValue = AirPodsBatteryCheckerState.Connecting)

    when (val state = state) {
        AirPodsBatteryCheckerState.Connecting -> Body(null)
        is AirPodsBatteryCheckerState.Connected -> Body(state.airPodsBatteryLevel)
        is AirPodsBatteryCheckerState.Error -> Body(AirPodsBatteryLevel(-1, -1, -1))
    }
}

@Composable
private fun Body(batteryLevel: AirPodsBatteryLevel?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SmallCard(batteryLevel = batteryLevel)
    }
}

@Composable
fun SmallCard(batteryLevel: AirPodsBatteryLevel?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.75f)
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(White)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_headphones), contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ursus AirPods Pro 2",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                ValueColumn(modifier = Modifier.weight(1f), label = "L", value = batteryLevel?.left)
                ValueColumn(modifier = Modifier.weight(1f), label = "R", value = batteryLevel?.right)
                ValueColumn(modifier = Modifier.weight(1f), label = "C", value = batteryLevel?.case)
            }
        }
    }
}

@Composable
private fun ValueColumn(modifier: Modifier, label: String, value: Int?) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        if (value == null) {
            PercentValue(value = "0", transparent = true)
        } else {
            PercentValue(value = value.toString(), transparent = false)
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
            style = MaterialTheme.typography.displaySmall,
            text = value,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            modifier = Modifier
                .alignByBaseline(),
            style = MaterialTheme.typography.displaySmall,
            text = "%",
            color = MaterialTheme.colorScheme.primary
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