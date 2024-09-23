package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
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
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.R
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.ui.theme.White
import com.tyom.feature_main.ui.views.common.SwitchButton
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.extensions.IfTrue
import com.tyom.ui_tools.text.settingsItemFont
import com.tyom.ui_tools.widgets.Loader
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    isKeyboardVisible: Boolean,
    isLoading: Boolean,
    selectedInstrument: Instrument?,
    instruments: List<Instrument>,
    notes: List<Note>,
    liveNotes: List<Pair<List<Note>, Int>>,

    onClickChangeKeyboardVisibility: () -> Unit,
    onClickRefreshInstruments: () -> Unit,
    onClickSelectInstrument: (Instrument) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen._20dp))
                ) {
                    Text(
                        text = stringResource(R.string.selected_device),
                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen._10dp))
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = (0.5).dp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedInstrument?.name.orEmpty(),
                                style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(R.dimen._12dp),
                                    vertical = dimensionResource(R.dimen._7dp)
                                )
                            )
                        }

                        Loader(
                            isLoading = isLoading,
                            circleSize = dimensionResource(R.dimen._6dp),
                            innerCircleSize = dimensionResource(R.dimen._7dp),
                            padding = dimensionResource(R.dimen._1dp),
                            color = Color.Black,
                            innerColor = White
                        )
                    }

                    Text(
                        text = stringResource(R.string.keyboard),
                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen._10dp))
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
                .padding(dimensionResource(R.dimen._10dp))
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
                        .align(Alignment.CenterStart)
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
        isLoading = true,
        selectedInstrument = Instrument(
            name = "Instrument name",
            address = "address"
        ),
        isKeyboardVisible = false,
        instruments = emptyList(),
        notes = emptyList(),
        liveNotes = emptyList(),
        onClickRefreshInstruments = {},
        onClickSelectInstrument = {},
        onClickChangeKeyboardVisibility = {}
    )
}