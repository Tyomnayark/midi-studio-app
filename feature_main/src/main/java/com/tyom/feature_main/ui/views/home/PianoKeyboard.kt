package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyom.feature_main.R
import com.tyom.feature_main.constants.PianoConstants.BLACK_KEYS_COUNT
import com.tyom.feature_main.constants.PianoConstants.WHITE_KEYS_COUNT
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.ui.theme.GrayLight
import com.tyom.feature_main.ui.theme.OrangeLight
import com.tyom.feature_main.ui.theme.PianoShadowGray
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
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen._12dp))
                        .width(dimensionResource(id = R.dimen._90dp))
                        .background(
                            color = Color.White, shape = keyShape
                        )
                        .border(width = (0.1).dp, shape = keyShape, color = GrayLight)
                ) {
                    notes.forEach { note ->
                        note.isWhiteKey.IfTrue {
                            if (note.value == keyIndex) {
                                Box(
                                    modifier = Modifier
                                        .height(dimensionResource(id = R.dimen._12dp))
                                        .width(dimensionResource(id = R.dimen._90dp))
                                        .background(
                                            color = OrangeLight, shape = keyShape
                                        )
                                )
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
                        .padding(top = dimensionResource(id = R.dimen._4dp))
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
                                            color = OrangeLight, shape = keyShape
                                        )
                                )
                            }
                        }
                    }
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
                    alpha = 0.07f
                )
        )
    }
}

@Composable
@Preview
fun PianoKeyboardPreview() {
    PianoKeyboard(notes = emptyList())
}