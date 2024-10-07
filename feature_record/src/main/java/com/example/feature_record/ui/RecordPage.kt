package com.example.feature_record.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.feature_record.models.SettingsState
import com.tyom.core_ui.R
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.extensions.IfTrue
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_ui.theme.GrayLight
import com.tyom.core_ui.theme.Red
import com.tyom.core_ui.theme.White
import com.tyom.core_ui.theme.ralewayExtraLightTextStyle
import com.tyom.core_ui.theme.ralewayMediumTextStyle
import com.tyom.core_ui.utils.getTextWidthInDp
import com.tyom.core_ui.widgets.DotsLoader
import com.tyom.core_ui.widgets.PulsatingCircle
import com.tyom.core_ui.widgets.ScaleAnimateContainer
import com.tyom.core_ui.widgets.SwitchButton
import com.tyom.core_utils.extensions.empty
import com.tyom.core_utils.extensions.isNotNull
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.Note
import kotlinx.coroutines.launch

private const val DURATION_ANIMATION = 500

@Composable
fun RecordPage(
    settingsState: SettingsState,
    pianoConfiguration: PianoConfiguration,
    notes: List<Note>,
    liveNotes: Map<Int, List<Note>>,
    mapSize: Int,
    isRecording: Boolean,

    onClickRecordBtn: () -> Unit,
    onChangeModalDrawerState: (Boolean) -> Unit,
    onClickChangeKeyboardVisibility: () -> Unit,
    onClickChangeAutoConnect: () -> Unit,
    onClickRefreshBluetoothInstruments: () -> Unit,
    onClickRefreshMidiInstruments: () -> Unit,
    onClickSelectBluetoothInstrument: (Instrument) -> Unit,
    onClickSelectMidiInstrument: (Instrument) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val containerScrollState = rememberScrollState()
    val heightBluetooth by animateDpAsState(
        targetValue = when {
            settingsState.isBluetoothLoading -> dimensionResource(R.dimen._32dp) * (settingsState.bluetoothInstruments.size) + dimensionResource(
                R.dimen._20dp
            )

            settingsState.isBluetoothInstrumentsListOpened -> dimensionResource(R.dimen._32dp) * (settingsState.bluetoothInstruments.size)
            else -> dimensionResource(R.dimen._0dp)
        },
        label = String.empty()
    )
    val widthPianoStrokesBlock =
        (pianoConfiguration.widthFromStrokes / LocalDensity.current.density).dp
    val recordBlockBtnsPadding = widthPianoStrokesBlock - dimensionResource(id = R.dimen._35dp)

    val heightMidi by animateDpAsState(
        targetValue = when {
            settingsState.isMidiLoading -> dimensionResource(R.dimen._32dp) * (settingsState.midiInstruments.size) + dimensionResource(
                R.dimen._20dp
            )

            settingsState.isMidiInstrumentsListOpened -> dimensionResource(R.dimen._32dp) * (settingsState.midiInstruments.size)
            else -> dimensionResource(R.dimen._0dp)
        },
        label = String.empty()
    )

    val bluetoothInstrumentsListPadding by animateDpAsState(
        targetValue = when {
            settingsState.isBluetoothLoading -> dimensionResource(R.dimen._32dp)
            else -> dimensionResource(R.dimen._10dp)
        }
    )

    val midiInstrumentsListPadding by animateDpAsState(
        targetValue = when {
            settingsState.isMidiLoading -> dimensionResource(R.dimen._32dp)
            else -> dimensionResource(R.dimen._10dp)
        }
    )

    val widthAnimated = animateDpAsState(
        targetValue = when {
            settingsState.isTryingToConnectBluetooth -> dimensionResource(R.dimen._60dp)
            settingsState.isConnectBluetoothBtnEnabled ->
                getTextWidthInDp(
                    text = stringResource(settingsState.connectBluetoothBtnText),
                    textStyle = ralewayMediumTextStyle(dimensionResource(R.dimen._15sp))
                ) + dimensionResource(R.dimen._44dp)

            else -> dimensionResource(R.dimen._0dp)
        },
        animationSpec = tween(
            durationMillis = DURATION_ANIMATION,
            easing = FastOutSlowInEasing
        ),
        label = String.empty()
    )

    LaunchedEffect(drawerState.isOpen) {
        onChangeModalDrawerState(drawerState.isOpen)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = White
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = containerScrollState)
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen._20dp))
                ) {
                    Text(
                        text = stringResource(R.string.selected_bluetooth_device),
                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen._10dp))
                    )
                    Row {
                        ScaleAnimateContainer(
                            onFinishClick = {
                                onClickRefreshBluetoothInstruments()
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = dimensionResource(R.dimen._150dp))
                                    .border(
                                        width = (0.5).dp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                    ),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (settingsState.selectedBluetoothInstrument?.name.isNotNull()) {
                                    Text(
                                        text = settingsState.selectedBluetoothInstrument?.name.orEmpty(),
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        )
                                    )
                                } else {
                                    Text(
                                        text = stringResource(R.string.selected_device_hint),
                                        style = ralewayExtraLightTextStyle(
                                            size = dimensionResource(
                                                R.dimen._15sp
                                            )
                                        ),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        ),
                                        color = GrayLight
                                    )
                                }
                            }
                        }

                        ScaleAnimateContainer(
                            modifier = Modifier,
                            onStartClick = {
                                settingsState.selectedBluetoothInstrument?.let { instrument ->
                                    onClickSelectBluetoothInstrument(instrument)
                                }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(R.dimen._10dp))
                                    .height(dimensionResource(R.dimen._32dp))
                                    .width(widthAnimated.value)
                                    .background(
                                        color = settingsState.connectBluetoothBtnColor,
                                        shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (settingsState.isTryingToConnectBluetooth) {
                                    DotsLoader(
                                        modifier = Modifier
                                            .padding(horizontal = dimensionResource(R.dimen._10dp)),
                                        circleSize = dimensionResource(R.dimen._6dp),
                                        innerCircleSize = dimensionResource(R.dimen._7dp),
                                        padding = dimensionResource(R.dimen._1dp),
                                        color = White,
                                        innerColor = settingsState.connectBluetoothBtnColor
                                    )
                                } else {
                                    Text(
                                        text = stringResource(settingsState.connectBluetoothBtnText),
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        ),
                                        color = White
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(heightBluetooth),
                    ) {
                        settingsState.isBluetoothLoading.IfTrue {
                            DotsLoader(
                                modifier = Modifier
                                    .padding(
                                        top = dimensionResource(R.dimen._10dp)
                                    ),
                                isLoading = settingsState.isBluetoothLoading,
                                circleSize = dimensionResource(R.dimen._6dp),
                                innerCircleSize = dimensionResource(R.dimen._7dp),
                                padding = dimensionResource(R.dimen._1dp),
                                color = Color.Black,
                                innerColor = White
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = bluetoothInstrumentsListPadding)
                        ) {
                            settingsState.bluetoothInstruments.forEach { instrument: Instrument ->
                                ScaleAnimateContainer(
                                    onFinishClick = {
                                        onClickSelectBluetoothInstrument(instrument)
                                    }
                                ) {
                                    Text(
                                        text = instrument.name,
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.height(dimensionResource(R.dimen._32dp))
                                    )
                                }
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen._10dp))
                            .height((0.5).dp)
                            .fillMaxWidth()
                            .background(color = Color.Black)
                    )

                    Text(
                        text = stringResource(R.string.selected_midi_device),
                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(vertical = dimensionResource(R.dimen._10dp))
                    )
                    Row {
                        ScaleAnimateContainer(
                            onFinishClick = {
                                onClickRefreshMidiInstruments()
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = dimensionResource(R.dimen._150dp))
                                    .border(
                                        width = (0.5).dp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                    ),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (settingsState.selectedMidiInstrument?.name.isNotNull()) {
                                    Text(
                                        text = settingsState.selectedMidiInstrument?.name.orEmpty(),
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        )
                                    )
                                } else {
                                    Text(
                                        text = stringResource(R.string.selected_device_hint),
                                        style = ralewayExtraLightTextStyle(
                                            size = dimensionResource(
                                                R.dimen._15sp
                                            )
                                        ),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        ),
                                        color = GrayLight
                                    )
                                }
                            }
                        }

                        ScaleAnimateContainer(
                            modifier = Modifier,
                            onStartClick = {
                                settingsState.selectedMidiInstrument?.let { instrument ->
                                    onClickSelectMidiInstrument(instrument)
                                }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(R.dimen._10dp))
                                    .height(dimensionResource(R.dimen._32dp))
                                    .width(widthAnimated.value)
                                    .background(
                                        color = settingsState.connectMidiBtnColor,
                                        shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (settingsState.isTryingToConnectMidi) {
                                    DotsLoader(
                                        modifier = Modifier
                                            .padding(horizontal = dimensionResource(R.dimen._10dp)),
                                        circleSize = dimensionResource(R.dimen._6dp),
                                        innerCircleSize = dimensionResource(R.dimen._7dp),
                                        padding = dimensionResource(R.dimen._1dp),
                                        color = White,
                                        innerColor = settingsState.connectMidiBtnColor
                                    )
                                } else {
                                    Text(
                                        text = stringResource(settingsState.connectMidiBtnText),
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        ),
                                        color = White
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(heightMidi),
                    ) {
                        settingsState.isMidiLoading.IfTrue {
                            DotsLoader(
                                modifier = Modifier
                                    .padding(
                                        top = dimensionResource(R.dimen._10dp)
                                    ),
                                isLoading = settingsState.isMidiLoading,
                                circleSize = dimensionResource(R.dimen._6dp),
                                innerCircleSize = dimensionResource(R.dimen._7dp),
                                padding = dimensionResource(R.dimen._1dp),
                                color = Color.Black,
                                innerColor = White
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = midiInstrumentsListPadding)
                        ) {
                            settingsState.midiInstruments.forEach { instrument: Instrument ->
                                ScaleAnimateContainer(
                                    onFinishClick = {
                                        onClickSelectMidiInstrument(instrument)
                                    }
                                ) {
                                    Text(
                                        text = instrument.name,
                                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.height(dimensionResource(R.dimen._32dp))
                                    )
                                }
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen._10dp))
                            .height((0.5).dp)
                            .fillMaxWidth()
                            .background(color = Color.Black)
                    )

                    Text(
                        text = stringResource(R.string.keyboard),
                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(vertical = dimensionResource(R.dimen._10dp))
                    )
                    SwitchButton(
                        isChecked = settingsState.isKeyboardVisible,
                        onClick = {
                            onClickChangeKeyboardVisibility()
                        }
                    )

                    Text(
                        text = stringResource(R.string.auto_connect),
                        style = ralewayMediumTextStyle(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(vertical = dimensionResource(R.dimen._10dp))
                    )
                    SwitchButton(
                        isChecked = settingsState.isAutoConnect,
                        onClick = {
                            onClickChangeAutoConnect()
                        }
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen._10dp))
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

            settingsState.isKeyboardVisible.IfTrue {
                PianoKeyboard(
                    modifier = Modifier
                        .align(settingsState.keyboardAlign)
                        .rotate(settingsState.keyboardAngle)
                        .scale(settingsState.keyboardScale),
                    notes = notes
                )
            }

            LiveNoteString(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(
                        vertical = dimensionResource(R.dimen._50dp)
                    ),
                pianoConfiguration = pianoConfiguration,
                liveNotes = liveNotes,
                mapSize = mapSize,
                width = widthPianoStrokesBlock
            )

            ScaleAnimateContainer(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = dimensionResource(id = R.dimen._55dp),
                        end = recordBlockBtnsPadding
                    )
                    .size(dimensionResource(id = R.dimen._30dp)),
                onStartClick = {
                    onClickRecordBtn()
                }
            ) {
                if (isRecording) {
                    PulsatingCircle(
                        color = Red,
                        size = dimensionResource(id = R.dimen._30dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen._30dp))
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    RecordPage(
        settingsState = SettingsState(),
        pianoConfiguration = PianoConfiguration(),
        notes = emptyList(),
        liveNotes = emptyMap(),
        mapSize = 1,
        isRecording = true,

        onClickRecordBtn = {},
        onChangeModalDrawerState = {},
        onClickRefreshBluetoothInstruments = {},
        onClickSelectBluetoothInstrument = {},
        onClickChangeKeyboardVisibility = {},
        onClickChangeAutoConnect = {},
        onClickRefreshMidiInstruments = {},
        onClickSelectMidiInstrument = {}
    )
}