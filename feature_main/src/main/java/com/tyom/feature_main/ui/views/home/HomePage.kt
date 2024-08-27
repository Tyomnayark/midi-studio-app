package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tyom.ui_tools.extensions.FigmaLargePreview

@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

    }
}

@com.tyom.ui_tools.extensions.FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage()
}