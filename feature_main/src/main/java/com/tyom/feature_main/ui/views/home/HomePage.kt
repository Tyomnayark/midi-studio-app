package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.R
import com.tyom.feature_main.models.Note
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.extensions.noRippleClickable

@Composable
fun HomePage(
    instruments: List<Instrument>,
    notes:  List<Note>,
    liveNotes: List<Pair<List<Note>, Int>>,

    onClickRefreshInstruments: () -> Unit,
    onClickSelectInstrument: (Instrument) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PianoKeyboard(
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen._30dp),
                    start = dimensionResource(R.dimen._10dp)
                )
                .rotate(180f),
            notes = notes
        )
        LiveNoteString(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(
                    vertical = dimensionResource(R.dimen._30dp)
                ),
            liveNotes = liveNotes
        )
    }
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
        liveNotes = emptyList(),
        onClickRefreshInstruments = {},
        onClickSelectInstrument = {}
    )
}