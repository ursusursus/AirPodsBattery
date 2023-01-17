package sk.ursus.airpodsbattery.ui

import android.content.Intent
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import sk.ursus.airpodsbattery.R
import sk.ursus.airpodsbattery.SecondActivity
import sk.ursus.airpodsbattery.checker.AirPodsBatteryChecker
import sk.ursus.airpodsbattery.checker.AirPodsBatteryCheckerState
import sk.ursus.airpodsbattery.checker.AirPodsBatteryLevel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalMaterial3Api
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(batteryChecker: AirPodsBatteryChecker) {
    val scrollBehavior = exitUntilCollapsedScrollBehaviorx()
    var y = 0f
    val foo = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            y += available.y
            Log.d("Default", "y1=$y")
            return Offset.Zero
        }

        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
            y += available.y
            Log.d("Default", "y2=$y")
            return Offset.Zero
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(foo),
//        topBar = { LargeTopAppBarx(title = { Text("Hello") }, scrollBehavior = scrollBehavior) }
    ) {
        LargeTopAppBarx(title = { Text("Hello") }, scrollBehavior = scrollBehavior)
//        LazyColumn(contentPadding = inner) {
//            items(40) {
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(16.dp),
//                    text = "Bayyy"
//                )
//            }
//        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            repeat(40) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    text = "Bayyy"
                )
            }
        }
    }
//    ScreenRoot(insetBottom = false) {
//        val state by batteryChecker.airPodsBatteryLevel().collectAsState()
//        val state by remember { batteryChecker.airPodsBatteryLevel() }
//            .collectAsStateWithLifecycle(initialValue = AirPodsBatteryCheckerState.Connecting)
//        val scrollState = rememberScrollState()
//
//        Log.d("Default", "state=$state")
//
//        val batteryLevel = when (val s = state) {
//            AirPodsBatteryCheckerState.Connecting -> null
//            is AirPodsBatteryCheckerState.Connected -> s.airPodsBatteryLevel
//            is AirPodsBatteryCheckerState.Error -> AirPodsBatteryLevel(0, 0, 0)
//        }
//        Body(batteryLevel, scrollState)
//        Body2()
//        Body3()
//    }
}

@Composable
private fun MyContent(
    state: AirPodsBatteryCheckerState,
    scrollState: ScrollState
) {
    when (state) {
        AirPodsBatteryCheckerState.Connecting -> Body(null, scrollState)
        is AirPodsBatteryCheckerState.Connected -> Body(state.airPodsBatteryLevel, scrollState)
        is AirPodsBatteryCheckerState.Error -> Body(AirPodsBatteryLevel(0, 0, 0), scrollState)
    }
}

@Composable
private fun MyContent2(
    state: AirPodsBatteryCheckerState,
    scrollState: ScrollState
) {
    val batteryLevel = when (state) {
        AirPodsBatteryCheckerState.Connecting -> null
        is AirPodsBatteryCheckerState.Connected -> state.airPodsBatteryLevel
        is AirPodsBatteryCheckerState.Error -> AirPodsBatteryLevel(0, 0, 0)
    }
    Body(batteryLevel, scrollState)
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
        BatteryWidget(
            modifier = Modifier.fillMaxWidth(fraction = 0.7f),
            batteryLevel = batteryLevel
        )
        Spacer(modifier = Modifier.height(120.dp))
        val context = LocalContext.current
        ElevatedButton(onClick = {
            context.startActivity(Intent(context, SecondActivity::class.java))
        }) {
            Text(text = "Go to")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button")
        }
        FilledTonalButton(onClick = { /*TODO*/ }) {
            Text(text = "Button")
        }
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Button")
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Button")
        }
        Spacer(modifier = Modifier.height(16.dp))
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
        contentPadding = PaddingValues(16.dp) + navigationBarHeight(),
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Body3() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White.copy(alpha = 0.1f)),
            count = 3,
            contentPadding = PaddingValues(32.dp),
            itemSpacing = 16.dp
        ) { page ->
            val color = when (page) {
                0 -> Color.Magenta
                1 -> Color.Yellow
                else -> Color.Blue
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(color)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White.copy(alpha = 0.1f)),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "x Dwdwqdw",
                    Modifier
                        .fillParentMaxWidth(0.95f)
                        .background(Color.Blue)
                        .padding(32.dp)
                )
            }
            item {
                Text(
                    text = "Dwdwqdw",
                    Modifier
                        .background(Color.Blue)
                        .padding(32.dp)
                )
            }
            item {
                Text(
                    text = "Dwdwqdw",
                    Modifier
                        .background(Color.Blue)
                        .padding(32.dp)
                )
            }
            item {
                Text(
                    text = "Dwdwqdw",
                    Modifier
                        .background(Color.Blue)
                        .padding(32.dp)
                )
            }
        }
    }
}

@Composable
private fun navigationBarHeight(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}

fun Modifier.heightOfStatusBar(): Modifier = composed {
    windowInsetsTopHeight(WindowInsets.systemBars)
}

@Composable
fun BatteryWidget(modifier: Modifier = Modifier, batteryLevel: AirPodsBatteryLevel?) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        ColumnedColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            batteryLevel = batteryLevel
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
                val left by animateIntAsState(targetValue = batteryLevel?.left ?: -1)
                val right by animateIntAsState(targetValue = batteryLevel?.right ?: -1)
                val case by animateIntAsState(targetValue = batteryLevel?.case ?: -1)
                ValueColumn(label = "L", value = left)
                ValueColumn(label = "R", value = right)
                ValueColumn(label = "C", value = case)
            }
        }
    }
}

private fun DrawScope.drawValueRect(color: Color, value: Int, xOffset: Float) {
    val progress = 1 - value / 100f
    val y = size.height * progress
    drawRect(
        color = color,
        topLeft = Offset(xOffset, y),
        size = Size(width = size.width / 3f + 1f, height = size.height)
    )
}

@Composable
private fun ValueColumn(label: String, value: Int) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        if (value == -1) {
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

@Composable
fun ColumnedColumn(
    modifier: Modifier = Modifier,
    batteryLevel: AirPodsBatteryLevel?,
    content: @Composable ColumnScope.() -> Unit
) {
    val columnColor = MaterialTheme.colorScheme.onTertiary
    val left by animateIntAsState(targetValue = batteryLevel?.left ?: 0)
    val right by animateIntAsState(targetValue = batteryLevel?.right ?: 0)
    val case by animateIntAsState(targetValue = batteryLevel?.case ?: 0)
//    Log.d("Default", "---- left=$left right=$right case=$case")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawValueRect(color = columnColor, value = left, xOffset = 0f)
                drawValueRect(color = columnColor, value = right, xOffset = size.width * 1 / 3f)
                drawValueRect(color = columnColor, value = case, xOffset = size.width * 2 / 3f)
            }
            .then(modifier),
        content = content
    )
}

@Suppress("StateFlowValueCalledInComposition")
@Composable
fun <T> Flow<T>.collectAsState(context: CoroutineContext = EmptyCoroutineContext): State<T> {
    val stateFlow = (this as? StateFlow<T>) ?: error("Not a StateFlow. Use `collectAsState(initial = ...) for that")
    return stateFlow.collectAsState(stateFlow.value, context)
}
