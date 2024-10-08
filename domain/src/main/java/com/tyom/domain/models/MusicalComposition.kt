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

fun Map<Int, List<Note>>.toMusicalComposition(title: String): MusicalComposition {
    val listNotePairs = this.map { (orderNumber, notes) ->
        NotePairs(
            notes = notes,
            orderNumber = orderNumber
        )
    }
    return MusicalComposition(
        title = title,
        notesPairs = listNotePairs
    )
}
