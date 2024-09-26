package com.tyom.feature_library.ui

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.tyom.core_ui.R
import com.tyom.core_ui.enums.FontEnum
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.extensions.noRippleClickable
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_ui.theme.PianoGray
import com.tyom.core_ui.theme.Red
import com.tyom.core_ui.theme.handjetRegularTextStyle
import com.tyom.core_ui.theme.ralewayMediumTextStyle
import com.tyom.domain.models.MusicalComposition

private const val BOTTOM_SHEET_ANIM_MILLIS = 300
private const val SHEET_HEIGHT_COEFF = 0.9f
private const val WIDTH_COEFF = 0.8f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryPage(
    isModalBottomSheetIsOpened: Boolean,
    pianoConfiguration: PianoConfiguration,
    compositions: List<MusicalComposition>,
    fontStyle: FontEnum,

    testSave: () -> Unit,
    notifyBottomSheetWasClosed: () -> Unit,
    onClickSaveComposition: (MusicalComposition) -> Unit,
    onClickDeleteComposition: (MusicalComposition) -> Unit,
    clickToChangeFontStyle: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        animationSpec = tween(durationMillis = BOTTOM_SHEET_ANIM_MILLIS, easing = EaseOutCubic)
    )
    val scrollState = rememberScrollState()
    val scrollStateModalSheet = rememberScrollState()

    LaunchedEffect(isModalBottomSheetIsOpened) {
        if (isModalBottomSheetIsOpened) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) {
            notifyBottomSheetWasClosed()
        }
    }

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
                Column(
                    Modifier
                        .padding(dimensionResource(id = R.dimen._20dp))
                        .verticalScroll(scrollStateModalSheet),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val fontSize =
                        with(LocalDensity.current) { dimensionResource(id = R.dimen._20sp).toSp() }

                    Text(
                        text = stringResource(id = R.string.click_to_change_font_style),
                        modifier = Modifier.noRippleClickable {
                            clickToChangeFontStyle()
                        },
                        style = TextStyle(
                            fontSize = fontSize,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(fontStyle.fontRes))
                        )
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen._10dp))
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen._10dp)),
                text = stringResource(id = R.string.library_title),
                style = ralewayMediumTextStyle(size = dimensionResource(id = R.dimen._20sp))
            )
            compositions.forEach { composition ->
                var isClicked by remember { mutableStateOf(false) }

                val animatedWidthFloat by animateFloatAsState(if (isClicked) WIDTH_COEFF else 1f)
                val animatedButtonAlphaFloat by animateFloatAsState(if (isClicked) 1f else 0f)

                Row {
                    LibraryNoteString(
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen._10dp))
                            .noRippleClickable {
                                isClicked = !isClicked
                            }
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen._10dp)))
                            .border(
                                width = (0.1).dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen._10dp))
                            )
                            .fillMaxWidth(animatedWidthFloat),
                        notes = composition.notesPairs,
                        pianoConfiguration = pianoConfiguration
                    )


                    Column {
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = animatedButtonAlphaFloat
                                }
                                .padding(
                                    start = dimensionResource(id = R.dimen._5dp),
                                    bottom = dimensionResource(id = R.dimen._5dp)
                                )
                                .height(dimensionResource(R.dimen._30dp))
                                .fillMaxWidth()
                                .clickable {
                                    onClickSaveComposition(composition)
                                }
                                .background(
                                    color = Color.Black,
                                    shape = RoundedCornerShape(dimensionResource(R.dimen._10dp))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .graphicsLayer {
                                        scaleX = animatedButtonAlphaFloat
                                    },
                                text = stringResource(id = R.string.common_save),
                                style = ralewayMediumTextStyle(
                                    size = dimensionResource(id = R.dimen._12sp),
                                    color = Color.White
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen._5dp))
                                .height(dimensionResource(R.dimen._30dp))
                                .fillMaxWidth(1f)
                                .clickable {
                                    onClickDeleteComposition(composition)
                                }
                                .background(
                                    color = Red,
                                    shape = RoundedCornerShape(dimensionResource(R.dimen._10dp))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = animatedButtonAlphaFloat
                                    },
                                text = stringResource(id = R.string.common_delete),
                                style = ralewayMediumTextStyle(
                                    size = dimensionResource(id = R.dimen._12sp),
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen._5dp)
                    )
                    .height(dimensionResource(R.dimen._30dp))
                    .fillMaxWidth()
                    .clickable {
                        testSave()
                    }
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(dimensionResource(R.dimen._10dp))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "add mocks",
                    style = handjetRegularTextStyle(
                        size = dimensionResource(id = R.dimen._20sp),
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
    LibraryPage(
        isModalBottomSheetIsOpened = false,
        compositions = listOf(
            MusicalComposition(),
            MusicalComposition()
        ),
        pianoConfiguration = PianoConfiguration(
            lineSpacing = 9f
        ),
        fontStyle = FontEnum.NUNITO_SANS_CONDENSED_EXTRA_LIGHT_ITALIC,

        testSave = {},
        notifyBottomSheetWasClosed = {},
        onClickSaveComposition = {},
        onClickDeleteComposition = {},
        clickToChangeFontStyle = {}
    )
}