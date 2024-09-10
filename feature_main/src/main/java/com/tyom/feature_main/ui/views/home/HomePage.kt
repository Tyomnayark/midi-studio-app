package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyom.domain.models.Instrument
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.extensions.noRippleClickable

@Composable
fun HomePage(
    instruments: List<Instrument>,
    notes:  List<Pair<Int, Int>>,

    onClickRefreshInstruments: () -> Unit,
    onClickSelectInstrument: (Instrument) -> Unit
) {
    PianoKeyboard(notes = notes)
    Column {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, Modifier
            .size(40.dp)
            .padding(5.dp)
            .noRippleClickable {
                onClickRefreshInstruments()
            }
        )
        instruments.forEach { instrument ->
            Text(
                text = "name: ${instrument.name} address: ${instrument.address} ",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    onClickSelectInstrument(instrument)
                }
            )
        }
        notes.forEach { note ->
            Text(
                text = "$note",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage(
        instruments = emptyList(),
        notes = emptyList(),
        onClickRefreshInstruments = {},
        onClickSelectInstrument = {}
    )
}