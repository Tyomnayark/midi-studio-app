package com.tyom.feature_main.ui.views

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.widgets.ScaleAnimateContainer
import com.tyom.utils.extensions.empty

private const val CLICK_INITIAL_VALUE = 1f
private const val ANIMATE_DURATION_DEFAULT = 500
private const val OFFSET = -10f
const val ANIMATE_DURATION_150 = 150

@Composable
fun BottomBar(
    bottomItems: List<BottomNavigationItem>,
    currentScreen: String?,
    onClickBottomItem: (BottomNavigationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .height(50.dp)
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
            }
        }
    }
}

@FigmaLargePreview
@Composable
fun BottomBarPreview() {
    BottomBar(
        listOf(
            BottomNavigationItem(
                screen = ScreensEnum.HOME,
                isSelected = false
            ),
            BottomNavigationItem(
                screen = ScreensEnum.SETTINGS,
                isSelected = false
            )
        ),
        currentScreen = String.empty(),
        onClickBottomItem = {}
    )
}