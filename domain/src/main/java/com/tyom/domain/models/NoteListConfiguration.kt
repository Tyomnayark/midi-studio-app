package com.tyom.domain.models

import android.graphics.Color
import com.tyom.core_ui.constants.NoteConstants.A0
import com.tyom.core_ui.constants.NoteConstants.A2
import com.tyom.core_ui.constants.NoteConstants.A5
import com.tyom.core_ui.constants.NoteConstants.A6
import com.tyom.core_ui.constants.NoteConstants.A7
import com.tyom.core_ui.constants.NoteConstants.B0
import com.tyom.core_ui.constants.NoteConstants.B2
import com.tyom.core_ui.constants.NoteConstants.B3
import com.tyom.core_ui.constants.NoteConstants.B5
import com.tyom.core_ui.constants.NoteConstants.B6
import com.tyom.core_ui.constants.NoteConstants.B7
import com.tyom.core_ui.constants.NoteConstants.C1
import com.tyom.core_ui.constants.NoteConstants.C2
import com.tyom.core_ui.constants.NoteConstants.C4
import com.tyom.core_ui.constants.NoteConstants.C6
import com.tyom.core_ui.constants.NoteConstants.C7
import com.tyom.core_ui.constants.NoteConstants.C8
import com.tyom.core_ui.constants.NoteConstants.D1
import com.tyom.core_ui.constants.NoteConstants.D2
import com.tyom.core_ui.constants.NoteConstants.D6
import com.tyom.core_ui.constants.NoteConstants.D7
import com.tyom.core_ui.constants.NoteConstants.E1
import com.tyom.core_ui.constants.NoteConstants.E2
import com.tyom.core_ui.constants.NoteConstants.E6
import com.tyom.core_ui.constants.NoteConstants.E7
import com.tyom.core_ui.constants.NoteConstants.F1
import com.tyom.core_ui.constants.NoteConstants.F6
import com.tyom.core_ui.constants.NoteConstants.F7
import com.tyom.core_ui.constants.NoteConstants.G1
import com.tyom.core_ui.constants.NoteConstants.G6
import com.tyom.core_ui.constants.NoteConstants.G7
import com.tyom.core_ui.enums.FontEnum

data class NoteListConfiguration(
    val color: Int = Color.BLACK,
    val backgroundColor: Int = Color.WHITE,

    val title: String,
    val font: FontEnum = FontEnum.PLAYFAIR_DISPLAY_ITALIC,

    val lineSpacing: Float = 22f,
    val lineCount: Int = 5,
    val noteCount: Int = 15,
    val firstBassNote: Int = B3,
    val a4Paddings: Float = 100f,
    val paddingForTitle: Float = lineSpacing * 5,

    val noteCountWithPadding: Int = noteCount + 1,
    val halfLineSpacing: Float = lineSpacing / 2,
    val topPadding: Float = 0f,
    val strokeWidth: Float = lineSpacing / 5,
    val padding: Float = lineSpacing,
    val paddingForBottomLine: Float = lineSpacing * 2,
    val notePaddingTop: Float = padding - (lineSpacing * 1.5f),
    val notePaddingBottom: Float = paddingForBottomLine - (lineSpacing * 1.5f),
    val widthFromStrokes: Float = lineSpacing * 31,
    val bassLinesStartX: Float = halfLineSpacing * 5 + padding,
    val topLinesStartX: Float = halfLineSpacing * 23 + padding,

    val trebleClefWidth: Int = (lineSpacing * 9).toInt(),
    val trebleClefHeight: Int = (lineSpacing * 12).toInt(),
    val trebleClefX: Float = topLinesStartX - lineSpacing * 2,
    val trebleClefY: Float = -lineSpacing * 3 + a4Paddings,

    val bassClefWidth: Int = (lineSpacing * 5).toInt(),
    val bassClefHeight: Int = (lineSpacing * 9).toInt(),
    val bassClefX: Float = bassLinesStartX,
    val bassClefY: Float = -lineSpacing + a4Paddings,

    val topNoteLinePadding: Float = lineSpacing * 1.7f,
    val bottomNoteLinePadding: Float = -lineSpacing * 0.2f,
    val noteWidth: Float = lineSpacing * 1.5f,

    val endEdgeX: Float = (topLinesStartX + lineSpacing * 4),
    val endEdgeY: Float = 0f + a4Paddings,
    val startEdgeX: Float = bassLinesStartX,
    val startEdgeY: Float = 0f + a4Paddings,

    val needLineNotesMap: MutableMap<Int, Int> = mutableMapOf(
        //top lines
        C4 to 1,

        A5 to 1,
        B5 to 1,
        C6 to 2,
        D6 to 2,
        E6 to 3,
        F6 to 3,
        G6 to 4,
        A6 to 4,
        B6 to 5,
        C7 to 6,
        D7 to 6,
        E7 to 7,
        F7 to 7,
        G7 to 8,
        A7 to 8,
        B7 to 9,
        C8 to 9,

        //bottom lines
        E2 to 1,
        D2 to 1,
        C2 to 2,
        B2 to 2,
        A2 to 3,
        G1 to 3,
        F1 to 4,
        E1 to 4,
        D1 to 5,
        C1 to 5,
        B0 to 6,
        A0 to 6
    ),

    val bottomLineNotesList: MutableList<Int> = mutableListOf(
        C4, E2, D2, C2, B2, A2, G1, F1, E1, D1, C1, B0, A0
    )
)
