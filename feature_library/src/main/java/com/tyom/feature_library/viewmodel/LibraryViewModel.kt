package com.tyom.feature_library.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tyom.domain.usecases.SaveAsA4JpegFileUseCase
import com.tyom.feature_library.viewmodel.LibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val saveAsA4JpegFileUseCase: SaveAsA4JpegFileUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    override fun onCleared() {
        // TODO:  @Tyom [9/26/24] { чистить реалм ресурсы }
        super.onCleared()
    }
}
