package com.example.feature_home.ui

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
import com.example.feature_home.models.Note
import com.tyom.core_ui.R
import com.tyom.core_ui.constants.PianoConstants.BLACK_KEYS_COUNT
import com.tyom.core_ui.constants.PianoConstants.WHITE_KEYS_COUNT
import com.tyom.core_ui.theme.GrayDark
import com.tyom.core_ui.theme.GrayLightDark
import com.tyom.core_ui.theme.PianoGray
import com.tyom.core_ui.theme.PianoShadowGray
import com.tyom.core_ui.theme.Red
import com.tyom.core_ui.extensions.IfFalse
import com.tyom.core_ui.extensions.IfTrue
import com.tyom.core_ui.extensions.shadow

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
                            .height(dimensionResource(id = R.dimen._10dp))
                            .width(dimensionResource(id = R.dimen._70dp))
                            .background(
                                color = PianoGray, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._10dp))
                            .width(dimensionResource(id = R.dimen._68dp))
                            .background(
                                color = PianoShadowGray, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._9dp))
                            .width(dimensionResource(id = R.dimen._66dp))
                            .background(
                                color = Color.White, shape = keyShape
                            )
                    ) {
                        notes.forEach { note ->
                            note.isWhiteKey.IfTrue {
                                if (note.value == keyIndex) {
                                    Box(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen._10dp))
                                            .width(dimensionResource(id = R.dimen._66dp))
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
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._10dp)))
                }
                Box(
                    modifier = Modifier
                        .offset(y = dimensionResource(id = R.dimen._4dp))
                        .padding(top = dimensionResource(id = R.dimen._3dp)),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._7dp))
                            .width(dimensionResource(id = R.dimen._38dp))
                            .background(
                                color = Color.Black, shape = keyShape
                            )
                    ) {
                        notes.forEach { note ->
                            note.isWhiteKey.IfFalse {
                                if (note.value == keyIndex) {
                                    Box(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen._10dp))
                                            .width(dimensionResource(id = R.dimen._38dp))
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
                            .height(dimensionResource(id = R.dimen._7dp))
                            .width(dimensionResource(id = R.dimen._35dp))
                            .background(
                                color = GrayLightDark, shape = keyShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen._4dp))
                            .width(dimensionResource(id = R.dimen._35dp))
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