package com.tyom.core_ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.tyom.core_ui.R
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.extensions.dpToPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun DisappearingAnimateContainer(
    isAnimationStarted: Boolean,

    roundedBordersRadius: Dp = dimensionResource(R.dimen._20dp),
    width: Dp = dimensionResource(R.dimen._200dp),
    height: Dp = dimensionResource(R.dimen._100dp),
    color: Color = Color.Black,

    detailedByWidth: Int = 85,
    speedParticles: Long = 2L,
    speedAnimation: Long = 1000L,
    spreadXStart: Double = -10.0,
    spreadXEnd: Double = 10.0,
    spreadYStart: Double = -8.0,
    spreadYEnd: Double = 7.0,
    particlesCountCoeff: Float = 1.5f,
    particleRadiusOffset: Float = 0.02f,
    alphaEasing: Easing = FastOutSlowInEasing,

    onAnimationEnd: () -> Unit = {}
) {
    val heightFloat = height.dpToPx()
    val widthFloat = width.dpToPx()
    val roundedBordersRadiusFloat = roundedBordersRadius.dpToPx()
    val radius = widthFloat / detailedByWidth

    var particles by remember {
        mutableStateOf(
            createParticles(
                color,
                radius,
                heightFloat,
                widthFloat,
                roundedBordersRadiusFloat,
                particlesCountCoeff
            )
        )
    }
    var isParticlesStarted by remember { mutableStateOf(false) }
    val animatedFloat = remember { Animatable(0f) }
    val animatedParticlesAlpha = remember { Animatable(0f) }

    LaunchedEffect(isAnimationStarted) {
        if (isAnimationStarted) {
            launch {
                launch {
                    animatedFloat.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = speedAnimation.toInt(),
                            easing = alphaEasing
                        )
                    )
                }
                delay(speedAnimation / 5L)
                launch {
                    animatedParticlesAlpha.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = (speedAnimation * 0.8).toInt())
                    )
                }
                delay(speedAnimation / 15L)
                launch {
                    animatedFloat.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = speedAnimation.toInt() / 3)
                    )
                }
                isParticlesStarted = true
                launch {
                    delay(speedAnimation)
                    animatedParticlesAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = speedAnimation.toInt() * 2)
                    )
                    isParticlesStarted = false
                    onAnimationEnd()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
    ) {
        Canvas(
            modifier = Modifier
                .graphicsLayer {
                    alpha = animatedParticlesAlpha.value
                }
        ) {
            particles.forEach { particle ->
                drawCircle(
                    color = particle.color,
                    radius = particle.radius,
                    center = Offset(particle.x, particle.y)
                )
            }
        }

        Box(
            modifier = Modifier
                .height(height)
                .width(width)
                .graphicsLayer {
                    alpha = animatedFloat.value
                }
                .background(
                    color = color,
                    shape = RoundedCornerShape(roundedBordersRadius)
                )

        )
    }

    LaunchedEffect(particles, isParticlesStarted) {

        while (particles.isNotEmpty() && isParticlesStarted) {
            particles = particles.map { particle ->
                val newX = particle.x + Random.nextDouble(spreadXStart, spreadXEnd).toFloat()
                val newY = particle.y + Random.nextDouble(spreadYStart, spreadYEnd).toFloat()

                if (particle.radius - particleRadiusOffset >= 0f) {
                    particle.copy(
                        x = newX,
                        y = newY,
                        radius = particle.radius - particleRadiusOffset
                    )
                } else {
                    particle
                }
            }
            delay(speedParticles)
        }
    }
}

data class Particle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Color
)

private fun createParticles(
    color: Color,
    radius: Float,
    height: Float,
    width: Float,
    roundedBordersRadiusFloat: Float,
    particlesCountCoeff: Float
): List<Particle> {
    val particles = mutableListOf<Particle>()

    val repeatX = (width / radius * particlesCountCoeff).toInt()
    val repeatY = (height / radius * particlesCountCoeff).toInt()

    for (i in 0 until repeatX) {
        for (j in 0 until repeatY) {
            val centerX = i * radius / particlesCountCoeff
            val centerY = j * radius / particlesCountCoeff

            if (centerY < roundedBordersRadiusFloat && centerX < roundedBordersRadiusFloat) {
                if (!isPointInsideCircle(
                        pointX = centerX,
                        pointY = centerY,
                        centerX = roundedBordersRadiusFloat,
                        centerY = roundedBordersRadiusFloat,
                        radius = roundedBordersRadiusFloat
                    )
                ) {
                    continue
                }
            }

            if (centerY > height - roundedBordersRadiusFloat && centerX < roundedBordersRadiusFloat) {
                if (!isPointInsideCircle(
                        pointX = centerX,
                        pointY = centerY,
                        centerX = roundedBordersRadiusFloat,
                        centerY = height - roundedBordersRadiusFloat,
                        radius = roundedBordersRadiusFloat
                    )
                ) {
                    continue
                }
            }


            if (centerY > height - roundedBordersRadiusFloat && centerX > width - roundedBordersRadiusFloat) {
                if (!isPointInsideCircle(
                        pointX = centerX,
                        pointY = centerY,
                        centerX = width - roundedBordersRadiusFloat,
                        centerY = height - roundedBordersRadiusFloat,
                        radius = roundedBordersRadiusFloat
                    )
                ) {
                    continue
                }
            }

            if (centerY < roundedBordersRadiusFloat && centerX > width - roundedBordersRadiusFloat) {
                if (!isPointInsideCircle(
                        pointX = centerX,
                        pointY = centerY,
                        centerX = width - roundedBordersRadiusFloat,
                        centerY = roundedBordersRadiusFloat,
                        radius = roundedBordersRadiusFloat
                    )
                ) {
                    continue
                }
            }

            particles.add(
                Particle(
                    x = centerX,
                    y = centerY,
                    radius = radius,
                    color = color
                )
            )
        }
    }
    return particles
}

private fun isPointInsideCircle(
    pointX: Float,
    pointY: Float,
    centerX: Float,
    centerY: Float,
    radius: Float,
): Boolean {
    val distanceSquared =
        (pointX - centerX) * (pointX - centerX) + (pointY - centerY) * (pointY - centerY)
    return distanceSquared <= radius * radius
}

@Composable
@FigmaLargePreview
fun DisappearingAnimateContainerPreview() {
    DisappearingAnimateContainer(
        isAnimationStarted = false
    ){

    }
}