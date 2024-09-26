package com.tyom.domain.models

import com.tyom.core_utils.extensions.empty

data class MusicalComposition (
    var title: String = String.empty(),
    var notesPairs: List<NotePairs> = emptyList()
)

data class NotePairs (
    val notes: List<Note> = emptyList(),
    val orderNumber: Int = 0
)