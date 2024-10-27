package com.tyom.core_ui.widgets

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tyom.core_ui.R
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.extensions.noRippleClickable
import com.tyom.core_ui.theme.ralewayThinTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ZERO_ALPHA = 0f
private const val FULL_ALPHA = 1f
private const val ZERO_SCALE = 0f
private const val FULL_SCALE = 4f
private const val DELAY = 1000
private const val HALF_DELAY = 500L
private const val HOVER_SCALE = 1.2f

@Composable
fun HoverAnimatedButton(
    @StringRes text: Int,
    height: Dp,
    width: Dp,
    textColor: Color,
    hoverTextColor: Color,
    hoverColor: Color,
    padding: Dp,
    shapeCornerRad: Dp,

    onClick: () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }
    val animatedFloatAlpha = remember { Animatable(ZERO_ALPHA) }
    val animatedFloatScale = remember { Animatable(ZERO_SCALE) }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            onClick()
            launch {
                launch {
                    animatedFloatAlpha.animateTo(
                        targetValue = FULL_ALPHA,
                        animationSpec = tween(
                            durationMillis = DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    launch {
                        animatedFloatScale.animateTo(
                            targetValue = FULL_SCALE,
                            animationSpec = tween(
                                durationMillis = DELAY,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                    delay(HALF_DELAY)
                    animatedFloatAlpha.animateTo(
                        targetValue = ZERO_ALPHA,
                        animationSpec = tween(
                            durationMillis = DELAY,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                delay(DELAY.toLong() + HALF_DELAY)
                animatedFloatScale.snapTo(targetValue = ZERO_SCALE)
                animatedFloatAlpha.snapTo(targetValue = ZERO_ALPHA)
                isClicked = false
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(top = padding)
            .height(height)
            .width(width)
            .noRippleClickable {
                isClicked = true
            }
            .clip(RoundedCornerShape(shapeCornerRad))
            .border(
                width = (0.1).dp,
                color = textColor,
                shape = RoundedCornerShape(shapeCornerRad)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = padding,
                    vertical = dimensionResource(R.dimen._5dp)
                ),
            text = stringResource(id = text),
            style = ralewayThinTextStyle(dimensionResource(R.dimen._20dp))
        )

        Box(
            modifier = Modifier
                .scale(HOVER_SCALE)
                .blur(dimensionResource(id = R.dimen._10dp))
                .size(width = width, height = width),
            contentAlignment = Alignment.Center

        ) {
            Box(
                modifier = Modifier
                    .height(height)
                    .width(height)
                .graphicsLayer {
                    alpha = animatedFloatAlpha.value
                    scaleX = animatedFloatScale.value
                    scaleY = animatedFloatScale.value
                }
                    .background(
                        color = hoverColor,
                        shape = CircleShape
                    )
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    alpha = animatedFloatAlpha.value
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = padding,
                        vertical = dimensionResource(R.dimen._5dp)
                    ),
                text = stringResource(id = text),
                style = ralewayThinTextStyle(
                    size = dimensionResource(R.dimen._20dp),
                    color = hoverTextColor
                )
            )
        }
    }
}

@Composable
@FigmaLargePreview
fun HoverAnimatedButtonPreview() {
    HoverAnimatedButton(
        text = R.string.export_to_jpeg,
        height = dimensionResource(id = R.dimen._50dp),
        width = dimensionResource(id = R.dimen._180dp),
        textColor = Color.Black,
        hoverColor = Color.Black,
        hoverTextColor = Color.White,
        padding = dimensionResource(id = R.dimen._10dp),
        shapeCornerRad = dimensionResource(R.dimen._10dp)
    ) {

    }
}