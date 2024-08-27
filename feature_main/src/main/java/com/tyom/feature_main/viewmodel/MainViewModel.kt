package com.tyom.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import com.tyom.domain.usecases.CheckHaveConnectedInstrumentUseCase
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import com.tyom.utils.launchOnDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkHaveConnectedInstrumentUseCase: CheckHaveConnectedInstrumentUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        launchOnDefault {
            val instrument = checkHaveConnectedInstrumentUseCase.checkHaveConnectedInstrument()
            val bottomItems = listOf(
                BottomNavigationItem(
                    screen = ScreensEnum.HOME,
                    isSelected = true
                ),
                BottomNavigationItem(
                    screen = ScreensEnum.SETTINGS,
                    isSelected = false
                )
            )

            _uiState.update { state ->
                state.copy(
                    selectedInstrument = instrument,
                    bottomItems = bottomItems
                )
            }
        }
    }

    fun onClickBottomItem(bottomItem: BottomNavigationItem) {
        val updatedBottomNavItemsList = _uiState.value.bottomItems.map { item ->
            if (item.screen == bottomItem.screen) {
                item.copy(
                    isSelected = true
                )
            } else {
                item.copy(
                    isSelected = false
                )
            }
        }

        _uiState.update { state ->
            state.copy(
                bottomItems = updatedBottomNavItemsList
            )
        }
    }
}