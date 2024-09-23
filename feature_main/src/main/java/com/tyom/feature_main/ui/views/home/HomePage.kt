package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.R
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.ui.views.common.SwitchButton
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.extensions.IfTrue
import com.tyom.ui_tools.extensions.noRippleClickable
import com.tyom.ui_tools.text.settingsItemFont
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    isKeyboardVisible: Boolean,
    instruments: List<Instrument>,
    notes: List<Note>,
    liveNotes: List<Pair<List<Note>, Int>>,

    onClickChangeKeyboardVisibility: () -> Unit,
    onClickRefreshInstruments: () -> Unit,
    onClickSelectInstrument: (Instrument) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen._20dp))
                ) {
                    Text(
                        text = stringResource(R.string.keyboard),
                        style = settingsItemFont( size = dimensionResource(R.dimen._15sp))
                    )
                    SwitchButton(
                        isChecked = isKeyboardVisible,
                        onClick = {
                            onClickChangeKeyboardVisibility()
                        }
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier.size(dimensionResource(R.dimen._30dp)),
                onClick = {
                    scope.launch { drawerState.open() }
                }
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen._24dp))
                )
            }

            isKeyboardVisible.IfTrue {
                PianoKeyboard(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen._30dp),
                            start = dimensionResource(R.dimen._20dp)
                        )
                        .rotate(180f),
                    notes = notes
                )
            }
            LiveNoteString(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(
                        vertical = dimensionResource(R.dimen._30dp)
                    ),
                liveNotes = liveNotes
            )
        }
    }

//    Column {
//        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, Modifier
//            .size(40.dp)
//            .padding(5.dp)
//            .noRippleClickable {
//                onClickRefreshInstruments()
//            }
//        )
//        instruments.forEach { instrument ->
//            Text(
//                text = "name: ${instrument.name} address: ${instrument.address} ",
//                color = Color.Black,
//                fontSize = 20.sp,
//                modifier = Modifier.clickable {
//                    onClickSelectInstrument(instrument)
//                }
//            )
//        }
//        notes.forEach { note ->
//            Text(
//                text = "$note",
//                color = Color.Black,
//                fontSize = 20.sp
//            )
//        }
//    }
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage(
        isKeyboardVisible = false,
        instruments = emptyList(),
        notes = emptyList(),
        liveNotes = emptyList(),
        onClickRefreshInstruments = {},
        onClickSelectInstrument = {},
        onClickChangeKeyboardVisibility = {}
    )
}