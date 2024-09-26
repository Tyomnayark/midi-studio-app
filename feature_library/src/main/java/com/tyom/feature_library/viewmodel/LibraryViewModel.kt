package com.tyom.feature_library.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tyom.core_ui.enums.FontEnum
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_utils.extensions.launchOnDefault
import com.tyom.core_utils.extensions.launchOnIO
import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.Note
import com.tyom.domain.models.NotePairs
import com.tyom.domain.usecases.DeleteMusicCompositionUseCase
import com.tyom.domain.usecases.GetAllMusicCompositionsUseCase
import com.tyom.domain.usecases.SaveAsA4JpegFileUseCase
import com.tyom.domain.usecases.SaveToDBUseCase
import com.tyom.feature_library.viewmodel.LibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val PIANO_LINE_SPACING = 10f

@HiltViewModel
class LibraryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val saveAsA4JpegFileUseCase: SaveAsA4JpegFileUseCase,
    private val saveToDBUseCase: SaveToDBUseCase,
    private val getAllMusicCompositionsUseCase: GetAllMusicCompositionsUseCase,
    private val deleteMusicCompositionUseCase: DeleteMusicCompositionUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        launchOnIO {
            val compositions = getAllMusicCompositionsUseCase.execute()

            val pianoConfiguration = PianoConfiguration(
                lineSpacing = PIANO_LINE_SPACING
            )
            _uiState.update { state ->
                state.copy(
                    pianoConfiguration = pianoConfiguration,
                    compositions = compositions
                )
            }
        }
    }

    fun onClickSaveComposition(musicalComposition: MusicalComposition) {
        launchOnDefault {

            _uiState.update { state ->
                state.copy(
                    isModalBottomSheetIsOpened = true
                )
            }
        }
    }

    fun saveCompositionToDB() {
        launchOnIO {
            val musicalComposition = MusicalComposition(
                title = "first composition",
                notesPairs = listOf(
                    NotePairs(
                        notes = listOf(
                            Note(
                                value = 10,
                                isWhiteKey = true
                            ),
                            Note(
                                value = 15,
                                isWhiteKey = true
                            )
                        ),
                        orderNumber = 1
                    ),
                    NotePairs(
                        notes = listOf(
                            Note(
                                value = 32,
                                isWhiteKey = true
                            ),
                            Note(
                                value = 15,
                                isWhiteKey = true
                            )
                        ),
                        orderNumber = 2
                    ),
                    NotePairs(
                        notes = listOf(
                            Note(
                                value = 13,
                                isWhiteKey = true
                            ),
                            Note(
                                value = 25,
                                isWhiteKey = true
                            )
                        ),
                        orderNumber = 3
                    )
                )
            )

            val result = saveToDBUseCase.execute(musicalComposition)
        }
    }

    fun notifyBottomSheetWasClosed() {
        _uiState.update { state ->
            state.copy(
                isModalBottomSheetIsOpened = false
            )
        }
    }

    fun onClickDeleteComposition(musicalComposition: MusicalComposition) {
        launchOnIO {
            val result = deleteMusicCompositionUseCase.execute(musicalComposition.title)

            val updatedListCompositions = _uiState.value.compositions.filter { composition ->
                composition.title != musicalComposition.title
            }

            _uiState.update { state ->
                state.copy(
                    compositions = updatedListCompositions
                )
            }
        }
    }

    fun clickToChangeFontStyle() {
        val newFontStyle = when (_uiState.value.fontStyle) {
            FontEnum.GOWUN_BATANG_BOLD -> FontEnum.GOWUN_BATANG_REGULAR
            FontEnum.GOWUN_BATANG_REGULAR -> FontEnum.HANDJET_LIGHT
            FontEnum.HANDJET_LIGHT -> FontEnum.HANDJET_MEDIUM
            FontEnum.HANDJET_MEDIUM -> FontEnum.HANDJET_REGULAR
            FontEnum.HANDJET_REGULAR -> FontEnum.NUNITO_SANS_CONDENSED_EXTRA_BOLD
            FontEnum.NUNITO_SANS_CONDENSED_EXTRA_BOLD -> FontEnum.NUNITO_SANS_CONDENSED_EXTRA_BOLD_ITALIC
            FontEnum.NUNITO_SANS_CONDENSED_EXTRA_BOLD_ITALIC -> FontEnum.NUNITO_SANS_CONDENSED_EXTRA_LIGHT
            FontEnum.NUNITO_SANS_CONDENSED_EXTRA_LIGHT -> FontEnum.NUNITO_SANS_CONDENSED_EXTRA_LIGHT_ITALIC
            FontEnum.NUNITO_SANS_CONDENSED_EXTRA_LIGHT_ITALIC -> FontEnum.NUNITO_SANS_CONDENSED_MEDIUM
            FontEnum.NUNITO_SANS_CONDENSED_MEDIUM -> FontEnum.NUNITO_SANS_CONDENSED_MEDIUM_ITALIC
            FontEnum.NUNITO_SANS_CONDENSED_MEDIUM_ITALIC -> FontEnum.PLAYFAIR_DISPLAY_BOLD
            FontEnum.PLAYFAIR_DISPLAY_BOLD -> FontEnum.PLAYFAIR_DISPLAY_BOLD_ITALIC
            FontEnum.PLAYFAIR_DISPLAY_BOLD_ITALIC -> FontEnum.PLAYFAIR_DISPLAY_ITALIC
            FontEnum.PLAYFAIR_DISPLAY_ITALIC -> FontEnum.PLAYFAIR_DISPLAY_REGULAR
            FontEnum.PLAYFAIR_DISPLAY_REGULAR -> FontEnum.PLAYWRITE_DEGRUND_REGULAR
            FontEnum.PLAYWRITE_DEGRUND_REGULAR -> FontEnum.PLAYWRITE_DEGRUND_THIN
            FontEnum.PLAYWRITE_DEGRUND_THIN -> FontEnum.RALEWAY_BLACK
            FontEnum.RALEWAY_BLACK -> FontEnum.RALEWAY_BLACK_ITALIC
            FontEnum.RALEWAY_BLACK_ITALIC -> FontEnum.RALEWAY_BLACK_EXTRA_LIGHT
            FontEnum.RALEWAY_BLACK_EXTRA_LIGHT -> FontEnum.RALEWAY_BLACK_EXTRA_LIGHT_ITALIC
            FontEnum.RALEWAY_BLACK_EXTRA_LIGHT_ITALIC -> FontEnum.RALEWAY_BLACK_MEDIUM
            FontEnum.RALEWAY_BLACK_MEDIUM -> FontEnum.RALEWAY_BLACK_MEDIUM_ITALIC
            FontEnum.RALEWAY_BLACK_MEDIUM_ITALIC -> FontEnum.RALEWAY_BLACK_THIN
            FontEnum.RALEWAY_BLACK_THIN -> FontEnum.RALEWAY_BLACK_THIN_ITALIC
            FontEnum.RALEWAY_BLACK_THIN_ITALIC -> FontEnum.SATISFY_REGULAR
            FontEnum.SATISFY_REGULAR -> FontEnum.GOWUN_BATANG_BOLD
        }
        _uiState.update { state ->
            state.copy(
                fontStyle = newFontStyle
            )
        }
    }

    override fun onCleared() {
        // TODO:  @Tyom [9/26/24] { чистить реалм ресурсы }
        super.onCleared()
    }
}
