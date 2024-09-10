package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.tyom.feature_main.R
import com.tyom.feature_main.constants.PianoConstants.BLACK_KEYS_COUNT
import com.tyom.feature_main.constants.PianoConstants.BLACK_KEY_TYPE
import com.tyom.feature_main.constants.PianoConstants.WHITE_KEYS_COUNT
import com.tyom.feature_main.constants.PianoConstants.WHITE_KEY_TYPE
import com.tyom.feature_main.ui.theme.GrayLight
import com.tyom.feature_main.ui.theme.OrangeLight
import com.tyom.feature_main.ui.theme.PianoShadowGray
import com.tyom.ui_tools.extensions.DevicePreviews
import com.tyom.ui_tools.extensions.shadow

@Composable
fun PianoKeyboard(
    modifier: Modifier = Modifier,
    notes: List<Pair<Int, Int>>
) {
    val keyShape = RoundedCornerShape(
        topEnd = dimensionResource(id = R.dimen._2dp),
        bottomEnd = dimensionResource(id = R.dimen._2dp))

    Box(
        modifier = Modifier
            .rotate(180f)
            .fillMaxSize()
    ) {
        Box(
            modifier = modifier
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
                        notes.forEach { pair ->
                            if (pair.second == WHITE_KEY_TYPE) {
                                if (pair.first == keyIndex) {
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
                        notes.forEach { pair ->
                            if (pair.second == BLACK_KEY_TYPE) {
                                if (pair.first == keyIndex) {
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
        }
    }
}

@Composable
@DevicePreviews
fun PianoKeyboardPreview() {
    PianoKeyboard(notes = emptyList())
}