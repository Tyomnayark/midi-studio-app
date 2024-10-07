package com.tyom.core_ui.widgets

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.tyom.core_ui.R
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_utils.extensions.empty

private const val DURATION = 1000
private const val SCALE_INIT = 0.8f
private const val SCALE_TARGET = 1f
private const val SCALE_SECOND_INIT = 0f
private const val SCALE_SECOND_TARGET = 1.5f
private const val ALPHA_INIT = 0.8f
private const val ALPHA_TARGET = 1f
private const val ALPHA_SECOND_INIT = 1f
private const val ALPHA_SECOND_TARGET = 0f

@Composable
fun PulsatingCircle(
    modifier: Modifier = Modifier,
    duration: Int = DURATION,
    scaleInit: Float = SCALE_INIT,
    scaleTarget: Float = SCALE_TARGET,
    scaleSecondInit: Float = SCALE_SECOND_INIT,
    scaleSecondTarget: Float = SCALE_SECOND_TARGET,
    alphaInit: Float = ALPHA_INIT,
    alphaTarget: Float = ALPHA_TARGET,
    alphaSecondInit: Float = ALPHA_SECOND_INIT,
    alphaSecondTarget: Float = ALPHA_SECOND_TARGET,
    size: Dp = dimensionResource(id = R.dimen._30dp),
    color: Color = Color.Black
) {
    val infiniteTransition = rememberInfiniteTransition(label = String.empty())

    val scale by infiniteTransition.animateFloat(
        initialValue = scaleInit,
        targetValue = scaleTarget,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = String.empty()
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = alphaInit,
        targetValue = alphaTarget,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = String.empty()
    )

    val alphaSecond by infiniteTransition.animateFloat(
        initialValue = alphaSecondInit,
        targetValue = alphaSecondTarget,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = String.empty()
    )

    val scaleSecond by infiniteTransition.animateFloat(
        initialValue = scaleSecondInit,
        targetValue = scaleSecondTarget,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = String.empty()
    )
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer(
                    scaleX = scaleSecond,
                    scaleY = scaleSecond,
                    alpha = alphaSecond
                )
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
    }

}

@FigmaLargePreview
@Composable
fun PulsatingCirclePreview() {
    PulsatingCircle()
}