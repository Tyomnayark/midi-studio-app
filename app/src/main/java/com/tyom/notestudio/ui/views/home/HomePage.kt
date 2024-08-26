package com.tyom.notestudio.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tyom.notestudio.ui.extensions.FigmaLargePreview

@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

    }
}

@FigmaLargePreview
@Composable
fun MainMenuPreview() {
    HomePage()
}