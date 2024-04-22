package com.example.bubblingshapes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun Heart(modifier: Modifier, horizontalPadding: Int, bottomMargin: Int) {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp - bottomMargin

    val yRandom = Random.nextInt(0, height / 4)
    val xRandom = Random.nextInt(horizontalPadding, (width - horizontalPadding))

    val state = remember { mutableStateOf(ShapeState.Show) }

    val offsetYAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            ShapeState.Show -> height.dp - 24.dp
            else -> yRandom.dp
        },
        animationSpec = tween(1200), label = ""
    )

    val offsetXAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            ShapeState.Show -> (((width - (horizontalPadding * 2)) / 2) + 12).dp
            else -> xRandom.dp
        },
        animationSpec = tween(1200), label = ""
    )

    LaunchedEffect(key1 = state, block = {
        state.value = when (state.value) {
            ShapeState.Show -> ShapeState.Hide
            ShapeState.Hide -> ShapeState.Show
        }
    })

    AnimatedVisibility(
        visible = state.value == ShapeState.Show,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 700))
    ) {
        Canvas(modifier = modifier
            .offset(y = offsetYAnimation, x = offsetXAnimation),
            onDraw = {
                val path = Path().apply {
                    heartPath(Size(80f, 80f))
                }

                drawPath(
                    path = path,
                    color = Color(0xFFC51F5D),
                )
            }
        )
    }
}

@Composable
fun Star(modifier: Modifier, horizontalPadding: Int, bottomMargin: Int) {
    val starColors = listOf(Color(0xFF1e2b58), Color(0xFFe7bb67), Color(0xFF614cbf), Color(0xFf091e36))
    val randomPosition = remember { mutableStateOf(Random.nextInt(0, starColors.size - 1)) }
    val randomXPadding = remember { mutableStateOf(Random.nextInt(8, 44)) }
    val angle = remember { mutableStateOf(Random.nextInt(10, 40)) }
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp - bottomMargin

    val yRandom = Random.nextInt(height / 2, height)
    val xRandom = Random.nextInt(horizontalPadding, (width - horizontalPadding))

    val state = remember { mutableStateOf(ShapeState.Show) }

    val offsetYAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            ShapeState.Show -> height.dp
            else -> yRandom.dp
        },
        animationSpec = tween(1300), label = ""
    )

    val offsetXAnimation: Dp by animateDpAsState(
        targetValue = when (state.value) {
            ShapeState.Show -> (((width - (horizontalPadding * 2)) / 2) + randomXPadding.value).dp
            else -> xRandom.dp
        },
        animationSpec = tween(1300), label = ""
    )

    LaunchedEffect(key1 = state, block = {
        state.value = when (state.value) {
            ShapeState.Show -> ShapeState.Hide
            ShapeState.Hide -> ShapeState.Show
        }
    })

    AnimatedVisibility(
        visible = state.value == ShapeState.Show,
        enter = fadeIn(animationSpec = tween(durationMillis = 400)),
        exit = fadeOut(animationSpec = tween(durationMillis = 800))
    ) {
        Canvas(modifier = modifier
            .offset(y = offsetYAnimation, x = offsetXAnimation),
            onDraw = {
                val starPath = Path().apply { createStarPath(5, angle.value.toFloat(), Size(60f, 60f)) }
                drawPath(
                    path = starPath,
                    color = starColors[randomPosition.value]
                )
            }
        )
    }
}

fun Path.createStarPath(numPoints: Int, radius: Float, size: Size): Path {
    val angle: Float = (Math.PI * 2).toFloat() / (2 * numPoints)
    val cx = size.width
    val cy = size.height

    this.reset()
    this.moveTo(cx + radius, cy)

    for (i in 1 until numPoints * 2) {
        val r = (if (i % 2 == 0) radius else radius / 2).toFloat()
        this.lineTo((cx + r * cos((i * angle).toDouble())).toFloat(), (cy + r * sin((i * angle).toDouble())).toFloat())
    }

    this.close()
    return this
}


fun Path.heartPath(size: Size): Path {

    val width: Float = size.width
    val height: Float = size.height

    // Starting point
    moveTo(width / 2, height / 5)

    // Upper left path
    cubicTo(
        5 * width / 14, 0f,
        0f, height / 15,
        width / 28, 2 * height / 5
    )

    // Lower left path
    cubicTo(
        width / 14, 2 * height / 3,
        3 * width / 7, 5 * height / 6,
        width / 2, height
    )

    // Lower right path
    cubicTo(
        4 * width / 7, 5 * height / 6,
        13 * width / 14, 2 * height / 3,
        27 * width / 28, 2 * height / 5
    )

    // Upper right path
    cubicTo(
        width, height / 15,
        9 * width / 14, 0f,
        width / 2, height / 5
    )
    return this
}


enum class ShapeState {
    Show,
    Hide
}
