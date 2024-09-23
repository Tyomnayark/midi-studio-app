package com.tyom.ui_tools.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tyom.ui_tools.extensions.FigmaLargePreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val LONG_DELAY = 1000
private const val SHORT_DELAY = 500
private const val PAUSE_DELAY = 100L
private const val FINISH_CYCLE_DELAY = 1000L
private const val FULL_CIRCLE = 1f
private const val ZERO_CIRCLE = 0f

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    circleSize: Dp = 10.dp,
    innerCircleSize: Dp = 12.dp,
    padding: Dp = 2.dp,
    color: Color = Color.Black,
    innerColor: Color = Color.White
) {
    val animatedFloatFirstInnerCircle = remember { Animatable(ZERO_CIRCLE) }
    val animatedFloatFirstOuterCircle = remember { Animatable(ZERO_CIRCLE) }

    val animatedFloatSecondInnerCircle = remember { Animatable(ZERO_CIRCLE) }
    val animatedFloatSecondOuterCircle = remember { Animatable(ZERO_CIRCLE) }

    val animatedFloatThirdInnerCircle = remember { Animatable(ZERO_CIRCLE) }
    val animatedFloatThirdOuterCircle = remember { Animatable(ZERO_CIRCLE) }

    val animatedFloatFourthInnerCircle = remember { Animatable(ZERO_CIRCLE) }
    val animatedFloatFourthOuterCircle = remember { Animatable(ZERO_CIRCLE) }

    LaunchedEffect(isLoading) {
        launch {
            while (isLoading) {
                launch {
                    animatedFloatFirstInnerCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(
                            durationMillis = LONG_DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animatedFloatFirstOuterCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(durationMillis = SHORT_DELAY)
                    )
                }
                delay(PAUSE_DELAY)
                launch {
                    animatedFloatSecondInnerCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(
                            durationMillis = LONG_DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animatedFloatSecondOuterCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(durationMillis = SHORT_DELAY)
                    )
                }
                delay(PAUSE_DELAY)
                launch {
                    animatedFloatThirdInnerCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(
                            durationMillis = LONG_DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animatedFloatThirdOuterCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(durationMillis = SHORT_DELAY)
                    )
                }
                delay(PAUSE_DELAY)
                launch {
                    animatedFloatFourthInnerCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(
                            durationMillis = LONG_DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animatedFloatFourthOuterCircle.animateTo(
                        targetValue = FULL_CIRCLE,
                        animationSpec = tween(durationMillis = SHORT_DELAY)
                    )
                }
                delay(FINISH_CYCLE_DELAY)
                animatedFloatFirstInnerCircle.snapTo(ZERO_CIRCLE)
                animatedFloatFirstOuterCircle.snapTo(ZERO_CIRCLE)
                animatedFloatSecondInnerCircle.snapTo(ZERO_CIRCLE)
                animatedFloatSecondOuterCircle.snapTo(ZERO_CIRCLE)
                animatedFloatThirdInnerCircle.snapTo(ZERO_CIRCLE)
                animatedFloatThirdOuterCircle.snapTo(ZERO_CIRCLE)
                animatedFloatFourthInnerCircle.snapTo(ZERO_CIRCLE)
                animatedFloatFourthOuterCircle.snapTo(ZERO_CIRCLE)
            }
        }
    }

    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatFirstOuterCircle.value
                        scaleY = animatedFloatFirstOuterCircle.value
                    }
                    .size(circleSize)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatFirstInnerCircle.value
                        scaleY = animatedFloatFirstInnerCircle.value
                    }
                    .size(innerCircleSize)
                    .background(
                        color = innerColor,
                        shape = CircleShape
                    )
            )
        }
        Box(
            modifier = Modifier
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatSecondOuterCircle.value
                        scaleY = animatedFloatSecondOuterCircle.value
                    }
                    .size(circleSize)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatSecondInnerCircle.value
                        scaleY = animatedFloatSecondInnerCircle.value
                    }
                    .size(innerCircleSize)
                    .background(
                        color = innerColor,
                        shape = CircleShape
                    )
            )
        }
        Box(
            modifier = Modifier
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatThirdOuterCircle.value
                        scaleY = animatedFloatThirdOuterCircle.value
                    }
                    .size(circleSize)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatThirdInnerCircle.value
                        scaleY = animatedFloatThirdInnerCircle.value
                    }
                    .size(innerCircleSize)
                    .background(
                        color = innerColor,
                        shape = CircleShape
                    )
            )
        }
        Box(
            modifier = Modifier
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatFourthOuterCircle.value
                        scaleY = animatedFloatFourthOuterCircle.value
                    }
                    .size(circleSize)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedFloatFourthInnerCircle.value
                        scaleY = animatedFloatFourthInnerCircle.value
                    }
                    .size(innerCircleSize)
                    .background(
                        color = innerColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

@FigmaLargePreview
@Composable
fun LoaderPreview() {
    Loader()
}