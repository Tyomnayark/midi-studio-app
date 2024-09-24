package com.tyom.ui_tools.widgets

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.min

@Composable
fun CircularLoader(
    strokeWidth: Int,
    color: Color
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(50)
            progress += 0.01f
        }
    }
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .rotate(angle)
    ) {
        val sweepAngle = 360 * progress
        val diameter = min(size.width, size.height)
        val radius = diameter / 2
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth.toFloat(), cap = StrokeCap.Round)
        )
    }
}
