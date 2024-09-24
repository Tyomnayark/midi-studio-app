package com.tyom.feature_main.ui.views.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import com.tyom.feature_main.R
import com.tyom.feature_main.ui.theme.GrayLight
import com.tyom.feature_main.ui.theme.Red
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.utils.extensions.empty

@Composable
fun SwitchButton(
    colorChecked: Color = Red,
    colorUnchecked: Color = GrayLight,
    isChecked: Boolean,

    onClick: () -> Unit
) {
    val switchBackgroundColor by animateColorAsState(
        targetValue = if (isChecked) colorChecked else colorUnchecked,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = String.empty()
    )

    val thumbOffsetX by animateDpAsState(
        targetValue = if (isChecked) dimensionResource(R.dimen._20dp) else dimensionResource(R.dimen._1dp),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = String.empty()
    )

    Box(
        modifier = Modifier
            .width(dimensionResource(R.dimen._53dp))
            .height(dimensionResource(R.dimen._28dp))
            .clip(RoundedCornerShape(dimensionResource(R.dimen._14dp)))
            .background(switchBackgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationX = thumbOffsetX.value
                }
                .offset(x = thumbOffsetX)
                .size(dimensionResource(R.dimen._24dp))
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@FigmaLargePreview
@Composable
fun SwitchButtonPreview() {
    Column {
        SwitchButton(
            isChecked = false
        ) { }
        Spacer(Modifier.height(dimensionResource(R.dimen._5dp)))
        SwitchButton(
            isChecked = true
        ) { }
    }
}