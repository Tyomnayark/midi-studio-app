package com.tyom.feature_main.ui.views

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_record.ui.RecordPage
import com.tyom.feature_library.ui.LibraryPage
import com.tyom.feature_library.ui.LibraryViewModel
import com.tyom.feature_main.models.Routes.LIBRARY_ROUTE
import com.tyom.feature_main.models.Routes.RECORD_ROUTE
import com.tyom.feature_main.viewmodel.MainUIState
import com.tyom.feature_main.viewmodel.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    state: MainUIState,
    mainViewModel: MainViewModel,
    onChangeModalDrawerState: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = RECORD_ROUTE) {
        composable(RECORD_ROUTE) {
            RecordPage(
                pianoConfiguration = state.pianoConfiguration,
                settingsState = state.settingsState,
                notes = state.currentNotes,
                liveNotes = state.liveNotes,
                mapSize = state.mapSize,
                isRecording = state.isRecording,

                onClickRecordBtn = {
                    mainViewModel.onClickRecordBtn()
                },
                onClickRefreshBluetoothInstruments = {
                    mainViewModel.onClickRefreshBluetoothInstruments()
                },
                onClickSelectBluetoothInstrument = { instrument ->
                    mainViewModel.onClickSelectBluetoothInstrument(instrument)
                },
                onClickChangeKeyboardVisibility = {
                    mainViewModel.changeKeyboardVisibility()
                },
                onClickChangeAutoConnect = {
                    mainViewModel.changeAutoConnect()
                },
                onChangeModalDrawerState = { isOpened ->
                    onChangeModalDrawerState(isOpened)
                },
                onClickRefreshMidiInstruments = {
                    mainViewModel.onClickRefreshMidiInstruments()
                },
                onClickSelectMidiInstrument = { instrument ->
                    mainViewModel.onClickSelectMidiInstrument(instrument)
                }
            )
        }

        composable(LIBRARY_ROUTE) {
            val libraryViewModel: LibraryViewModel = hiltViewModel()
            val state = libraryViewModel.uiState.collectAsStateWithLifecycle().value

            LibraryPage(
                isModalBottomSheetIsOpened = state.isModalBottomSheetIsOpened,
                compositions = state.compositions,
                pianoConfiguration = state.pianoConfiguration,
                fontStyle = state.fontStyle,

                testSave = {
                    libraryViewModel.saveCompositionToDB()
                },
                notifyBottomSheetWasClosed = {
                    libraryViewModel.notifyBottomSheetWasClosed()
                },
                onClickSaveComposition = { musicalComposition ->
                    libraryViewModel.onClickSaveComposition(musicalComposition)
                },
                onClickDeleteComposition = {
                        musicalComposition ->
                    libraryViewModel.onClickDeleteComposition(musicalComposition)
                },
                clickToChangeFontStyle = {
                    libraryViewModel.clickToChangeFontStyle()
                },
                onClickExportJpeg = { title: String ->  
                    libraryViewModel.saveAsA4JpegFile(title)
                },
                onClickExportPdf = { title: String ->
                    // TODO: add this feature later
                }
            )
        }
    }
}