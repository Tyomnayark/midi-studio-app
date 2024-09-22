package com.tyom.feature_main.ui.views.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.tyom.ui_tools.extensions.FigmaLargePreview

@Composable
fun SwitchButton(
    isChecked: Boolean,

    onClick: () -> Unit
) {
    
}

@FigmaLargePreview
@Composable
fun SwitchButtonPreview(){
    Column {
        SwitchButton(
            isChecked = false
        ) { }
        SwitchButton(
            isChecked = true
        ) { }
    }
}