package com.tyom.feature_main.ui.views

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tyom.core_ui.R
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.theme.ralewayMediumTextStyle
import com.tyom.core_ui.widgets.ScaleAnimateContainer
import com.tyom.core_utils.extensions.empty

private const val CLICK_INITIAL_VALUE = 1f
private const val ANIMATE_DURATION_DEFAULT = 500
private const val OFFSET = -10f
const val ANIMATE_DURATION_150 = 150

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    bottomItems: List<BottomNavigationItem>,
    currentScreen: String?,
    onClickBottomItem: (BottomNavigationItem) -> Unit
) {
    val brush = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.White
        )
    )

    Row(
        modifier = modifier
            .background(brush = brush)
            .height(dimensionResource(R.dimen._52dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        bottomItems.forEach { item ->
            val isSelected = currentScreen == item.screen.route

            val alphaAnimated by animateFloatAsState(
                targetValue = if (isSelected) CLICK_INITIAL_VALUE else 0f,
                animationSpec = tween(ANIMATE_DURATION_DEFAULT, easing = LinearOutSlowInEasing),
                label = String.empty()
            )

            val offsetClick by animateFloatAsState(
                targetValue = if (isSelected) OFFSET else 0f,
                animationSpec = tween(ANIMATE_DURATION_150, easing = LinearOutSlowInEasing),
                label = String.empty()
            )

            ScaleAnimateContainer(
                modifier = Modifier
                    .graphicsLayer {
                        translationY = offsetClick
                    },
                isEnabled = !isSelected,
                onStartClick = {
                    onClickBottomItem(item)
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.screen.iconId),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillHeight
                        )
                        Image(
                            painter = painterResource(id = item.screen.iconFilledId),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    alpha = alphaAnimated
                                },
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = alphaAnimated
                            }
                            .padding(dimensionResource(id = R.dimen._1dp)),
                        text = stringResource(id = item.resIdString),
                        color = Color.Black,
                        style = ralewayMediumTextStyle(size = dimensionResource(id = R.dimen._11sp))
                    )
                }
            }
        }
    }
}

@FigmaLargePreview
@Composable
fun BottomBarPreview() {
    BottomBar(
        modifier = Modifier,
        listOf(
            BottomNavigationItem(
                screen = ScreensEnum.RECORD,
                resIdString = R.string.record_screen,
                isSelected = false
            ),
            BottomNavigationItem(
                screen = ScreensEnum.LIBRARY,
                resIdString = R.string.library_screen,
                isSelected = false
            )
        ),
        currentScreen = String.empty(),
        onClickBottomItem = {}
    )
}