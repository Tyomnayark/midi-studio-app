package com.tyom.feature_main.ui.views.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.R
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.models.SettingsState
import com.tyom.feature_main.ui.theme.GrayLight
import com.tyom.feature_main.ui.theme.White
import com.tyom.feature_main.ui.views.common.SwitchButton
import com.tyom.ui_tools.extensions.FigmaLargePreview
import com.tyom.ui_tools.extensions.IfTrue
import com.tyom.ui_tools.text.settingsItemFont
import com.tyom.ui_tools.widgets.DotsLoader
import com.tyom.ui_tools.widgets.ScaleAnimateContainer
import com.tyom.utils.extensions.empty
import com.tyom.utils.extensions.isNotNull
import com.tyom.ui_tools.utils.getTextWidthInDp
import kotlinx.coroutines.launch

private const val DURATION_ANIMATION = 500

@Composable
fun HomePage(
    settingsState: SettingsState,
    notes: List<Note>,
    liveNotes: List<Pair<List<Note>, Int>>,

    onClickChangeKeyboardVisibility: () -> Unit,
    onClickChangeAutoConnect: () -> Unit,
    onClickRefreshInstruments: () -> Unit,
    onClickSelectInstrument: (Instrument) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val containerScrollState = rememberScrollState()
    val height by animateDpAsState(
        targetValue = when {
            settingsState.isLoading -> dimensionResource(R.dimen._32dp) * (settingsState.instruments.size) + dimensionResource(
                R.dimen._20dp
            )

            settingsState.isInstrumentsListOpened -> dimensionResource(R.dimen._32dp) * (settingsState.instruments.size)
            else -> dimensionResource(R.dimen._0dp)
        },
        label = String.empty()
    )
    val instrumentsListPadding by animateDpAsState(
        targetValue = when {
            settingsState.isLoading -> dimensionResource(R.dimen._32dp)
            else -> dimensionResource(R.dimen._10dp)
        }
    )

    val widthAnimated = animateDpAsState(
        targetValue = when {
            settingsState.isTryingToConnect -> dimensionResource(R.dimen._60dp)
            settingsState.isConnectBtnEnabled ->
                getTextWidthInDp(
                    text = stringResource(settingsState.connectBtnText),
                    textStyle = settingsItemFont(dimensionResource(R.dimen._15sp))
                ) + dimensionResource(R.dimen._44dp)

            else -> dimensionResource(R.dimen._0dp)
        },
        animationSpec = tween(
            durationMillis = DURATION_ANIMATION,
            easing = FastOutSlowInEasing
        ),
        label = String.empty()
    )

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
                        text = stringResource(R.string.selected_device),
                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen._10dp))
                    )
                    Row {
                        ScaleAnimateContainer(
                            onFinishClick = {
                                onClickRefreshInstruments()
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
                                if (settingsState.selectedInstrument?.name.isNotNull()) {
                                    Text(
                                        text = settingsState.selectedInstrument?.name.orEmpty(),
                                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(R.dimen._12dp),
                                            vertical = dimensionResource(R.dimen._7dp)
                                        )
                                    )
                                } else {
                                    Text(
                                        text = stringResource(R.string.selected_device_hint),
                                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
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
                                settingsState.selectedInstrument?.let { instrument ->
                                    onClickSelectInstrument(instrument)
                                }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = dimensionResource(R.dimen._10dp))
                                    .height(dimensionResource(R.dimen._32dp))
                                    .width(widthAnimated.value)
                                    .background(
                                        color = settingsState.connectBtnColor,
                                        shape = RoundedCornerShape(dimensionResource(R.dimen._15dp))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (settingsState.isTryingToConnect) {
                                    DotsLoader(
                                        modifier = Modifier
                                            .padding(horizontal = dimensionResource(R.dimen._10dp)),
                                        circleSize = dimensionResource(R.dimen._6dp),
                                        innerCircleSize = dimensionResource(R.dimen._7dp),
                                        padding = dimensionResource(R.dimen._1dp),
                                        color = White,
                                        innerColor = settingsState.connectBtnColor
                                    )
                                } else {
                                    Text(
                                        text = stringResource(settingsState.connectBtnText),
                                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
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
                            .height(height),
                    ) {
                        settingsState.isLoading.IfTrue {
                            DotsLoader(
                                modifier = Modifier
                                    .padding(
                                        top = dimensionResource(R.dimen._10dp)
                                    ),
                                isLoading = settingsState.isLoading,
                                circleSize = dimensionResource(R.dimen._6dp),
                                innerCircleSize = dimensionResource(R.dimen._7dp),
                                padding = dimensionResource(R.dimen._1dp),
                                color = Color.Black,
                                innerColor = White
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = instrumentsListPadding)
                        ) {
                            settingsState.instruments.forEach { instrument: Instrument ->
                                ScaleAnimateContainer(
                                    onFinishClick = {
                                        onClickSelectInstrument(instrument)
                                    }
                                ) {
                                    Text(
                                        text = instrument.name,
                                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
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
                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
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
                        style = settingsItemFont(size = dimensionResource(R.dimen._15sp)),
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

            settingsState.isKeyboardVisible.IfTrue {
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
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage(
        settingsState = SettingsState(),
        notes = emptyList(),
        liveNotes = emptyList(),
        onClickRefreshInstruments = {},
        onClickSelectInstrument = {},
        onClickChangeKeyboardVisibility = {},
        onClickChangeAutoConnect = {}
    )
}