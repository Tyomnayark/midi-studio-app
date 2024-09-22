package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.tyom.feature_main.R
import com.tyom.feature_main.constants.PianoConstants.BLACK_KEYS_COUNT
import com.tyom.feature_main.constants.PianoConstants.WHITE_KEYS_COUNT
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.ui.theme.GrayDark
import com.tyom.feature_main.ui.theme.GrayLightDark
import com.tyom.feature_main.ui.theme.PianoGray
import com.tyom.feature_main.ui.theme.PianoShadowGray
import com.tyom.feature_main.ui.theme.Red
import com.tyom.ui_tools.extensions.IfFalse
import com.tyom.ui_tools.extensions.IfTrue
import com.tyom.ui_tools.extensions.shadow

@Composable
fun PianoKeyboard(
    modifier: Modifier = Modifier,
    notes: List<Note>
) {
    val keyShape = RoundedCornerShape(
        topEnd = dimensionResource(id = R.dimen._2dp),
        bottomEnd = dimensionResource(id = R.dimen._2dp)
    )

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .shadow(
                color = PianoShadowGray,
                borderRadius = dimensionResource(id = R.dimen._2dp),
                blurRadius = dimensionResource(id = R.dimen._30dp)
            )
    ) {
        Column {
            (0 until WHITE_KEYS_COUNT).reversed().forEach { keyIndex ->
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._12dp))
                            .width(dimensionResource(id = R.dimen._94dp))
                            .background(
                                color = PianoGray, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._12dp))
                            .width(dimensionResource(id = R.dimen._92dp))
                            .background(
                                color = PianoShadowGray, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._11dp))
                            .width(dimensionResource(id = R.dimen._90dp))
                            .background(
                                color = Color.White, shape = keyShape
                            )
                    ) {
                        notes.forEach { note ->
                            note.isWhiteKey.IfTrue {
                                if (note.value == keyIndex) {
                                    Box(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen._12dp))
                                            .width(dimensionResource(id = R.dimen._90dp))
                                            .background(
                                                color = Red, shape = keyShape
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Column {
            (0 until BLACK_KEYS_COUNT).reversed().forEach { keyIndex ->
                val position = keyIndex % 5
                if (position == 2 || position == 0) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._12dp)))
                }
                Box(
                    modifier = Modifier
                        .offset(y = dimensionResource(id = R.dimen._4dp))
                        .padding(top = dimensionResource(id = R.dimen._4dp)),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._8dp))
                            .width(dimensionResource(id = R.dimen._50dp))
                            .background(
                                color = Color.Black, shape = keyShape
                            )
                    ) {
                        notes.forEach { note ->
                            note.isWhiteKey.IfFalse {
                                if (note.value == keyIndex) {
                                    Box(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen._12dp))
                                            .width(dimensionResource(id = R.dimen._80dp))
                                            .background(
                                                color = Red, shape = keyShape
                                            )
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._8dp))
                            .width(dimensionResource(id = R.dimen._48dp))
                            .background(
                                color = GrayLightDark, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._4dp))
                            .width(dimensionResource(id = R.dimen._48dp))
                            .background(
                                color = GrayDark, shape = keyShape
                            )
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .horizontalGradient(
                            listOf(
                                Color.Black,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent
                            )
                        ),
                    alpha = 0.05f
                )
        )
    }
}

@Composable
@Preview
fun PianoKeyboardPreview() {
    PianoKeyboard(notes = emptyList())
}