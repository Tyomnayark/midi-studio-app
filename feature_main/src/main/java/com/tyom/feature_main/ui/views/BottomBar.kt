package com.tyom.feature_main.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.widgets.SpinAnimateContainer
import com.tyom.utils.empty

@Composable
fun BottomBar(
    bottomItems: List<BottomNavigationItem>,
    currentScreen: String?,
    onClickBottomItem: (BottomNavigationItem) -> Unit
) {
    val itemModifier = Modifier
        .height(35.dp)

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomItems.forEach { item ->
            val isSelected = currentScreen == item.screen.route

            SpinAnimateContainer(
                isEnabled = !isSelected,
                onStartClick = {
                    onClickBottomItem(item)
                }
            ) {
                Image(
                    painter = painterResource(id = item.getIcon(isSelected)),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
                    contentDescription = null,
                    modifier = itemModifier,
                    contentScale = ContentScale.FillHeight
                )
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