package com.tyom.ui_tools.widgets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.tyom.utils.empty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PREVIEW_INITIAL_VALUE = 1f
private const val PREVIEW_TARGET_VALUE = 1.1f
private const val PREVIEW_DURATION_MILLIS = 500
private const val PREVIEW_START_DURATION_MILLIS = 300L
private const val CLICK_INITIAL_VALUE = 1f
private const val CLICK_TARGET_VALUE = 0f
const val ANIMATE_DURATION_DEFAULT = 125
const val TIMEOUT = 400L

@Composable
fun SpinAnimateContainer(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isEnabledState: MutableState<Boolean> = mutableStateOf(true),
    isAnimateScalePreviewShow: Boolean = false,
    animateDurationMillis: Int = ANIMATE_DURATION_DEFAULT,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    onStartClick: (() -> Unit)? = null,
    onFinishClick: () -> Unit = {},
    content: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    var isBoxClicked by remember { mutableStateOf(false) }
    var isEnabledClick by remember { mutableStateOf(true) }
    val scaleAnimation = rememberInfiniteTransition(label = String.empty())
    val coroutineScope = rememberCoroutineScope()

    val scalePreviewAnimate by scaleAnimation.animateFloat(
        initialValue = PREVIEW_INITIAL_VALUE,
        targetValue = PREVIEW_TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = PREVIEW_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        ),
        label = String.empty()
    )

    var anim by remember { mutableStateOf(false) }

    if (isAnimateScalePreviewShow) {
        LaunchedEffect(Unit) {
            delay(PREVIEW_START_DURATION_MILLIS)
            anim = true
        }
    }

    val scalePreview = if (anim) scalePreviewAnimate else PREVIEW_INITIAL_VALUE

    val scaleClick by animateFloatAsState(
        targetValue = if (isBoxClicked) CLICK_TARGET_VALUE else CLICK_INITIAL_VALUE,
        animationSpec = tween(animateDurationMillis, easing = FastOutSlowInEasing),
        finishedListener = {
            if (isBoxClicked) {
                isBoxClicked = !isBoxClicked
            } else {
                isEnabledState.value = true
                onFinishClick()
                isEnabledClick = true
            }
        },
        label = String.empty()
    )

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !isBoxClicked && isEnabledClick && isEnabledState.value && isEnabled
            ) {
                if (isEnabledClick && isEnabledState.value) {
                    isEnabledState.value = false
                    isBoxClicked = true
                    isEnabledClick = false
                    onStartClick?.invoke()
                } else {
                    coroutineScope.launch {
                        delay(TIMEOUT)
                        isEnabledState.value = true
                    }
                }
            }
            .graphicsLayer(
                scaleY = scalePreview,
                transformOrigin = transformOrigin
            )
            .graphicsLayer(
                scaleY = scaleClick,
                transformOrigin = transformOrigin
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}