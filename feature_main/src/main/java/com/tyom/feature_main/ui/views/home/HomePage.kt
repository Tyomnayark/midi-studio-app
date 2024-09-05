package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tyom.domain.models.Instrument
import com.tyom.ui_tools.extensions.FigmaLargePreview

@Composable
fun HomePage(
    instruments: List<Instrument>
) {
    Column {
        instruments.forEach { instrument ->
            Text(text = instrument.name, color = Color.Black)
        }
    }
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage(
        instruments = emptyList()
    )
}