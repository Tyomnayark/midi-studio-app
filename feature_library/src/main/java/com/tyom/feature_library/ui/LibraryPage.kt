package com.tyom.feature_library.ui

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.tyom.core_ui.R
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.theme.PianoGray
import com.tyom.core_ui.theme.ralewayMediumTextStyle
import kotlinx.coroutines.launch

private const val BOTTOM_SHEET_ANIM_MILLIS = 300
private const val SHEET_HEIGHT_COEFF = 0.9f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryPage() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        skipHalfExpanded = true,
        animationSpec = tween(durationMillis = BOTTOM_SHEET_ANIM_MILLIS, easing = EaseOutCubic)
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(SHEET_HEIGHT_COEFF)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen._20dp))
                    )
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen._5dp))
                        .height(dimensionResource(id = R.dimen._5dp))
                        .width(dimensionResource(id = R.dimen._40dp))
                        .background(
                            color = PianoGray,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen._5dp))
                        )
                        .align(Alignment.TopCenter)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen._10dp))
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .height(dimensionResource(R.dimen._30dp))
                    .width(dimensionResource(id = R.dimen._120dp))
                    .clickable {
                        scope
                            .launch {
                                sheetState.show()
                            }
                            .invokeOnCompletion {
                                showBottomSheet = true
                            }
                    }
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(dimensionResource(R.dimen._10dp))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.common_save),
                    style = ralewayMediumTextStyle(
                        size = dimensionResource(id = R.dimen._16sp),
                        color = Color.White
                    )
                )
            }
        }
    }
}

@FigmaLargePreview
@Composable
fun LibraryPagePreview() {
    LibraryPage()
}