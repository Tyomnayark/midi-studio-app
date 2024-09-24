package com.example.feature_home.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tyom.domain.models.NoteConstants.A0
import com.tyom.domain.models.NoteConstants.A1
import com.tyom.domain.models.NoteConstants.A2
import com.tyom.domain.models.NoteConstants.A3
import com.tyom.domain.models.NoteConstants.A4
import com.tyom.domain.models.NoteConstants.A5
import com.tyom.domain.models.NoteConstants.A6
import com.tyom.domain.models.NoteConstants.A7
import com.tyom.domain.models.NoteConstants.Ad0_Bb0
import com.tyom.domain.models.NoteConstants.Ad1_Bb1
import com.tyom.domain.models.NoteConstants.Ad2_Bb2
import com.tyom.domain.models.NoteConstants.Ad3_Bb3
import com.tyom.domain.models.NoteConstants.Ad4_Bb4
import com.tyom.domain.models.NoteConstants.Ad5_Bb5
import com.tyom.domain.models.NoteConstants.Ad6_Bb6
import com.tyom.domain.models.NoteConstants.Ad7_Bb7
import com.tyom.domain.models.NoteConstants.B0
import com.tyom.domain.models.NoteConstants.B2
import com.tyom.domain.models.NoteConstants.B3
import com.tyom.domain.models.NoteConstants.B4
import com.tyom.domain.models.NoteConstants.B5
import com.tyom.domain.models.NoteConstants.B6
import com.tyom.domain.models.NoteConstants.B7
import com.tyom.domain.models.NoteConstants.C1
import com.tyom.domain.models.NoteConstants.C3
import com.tyom.domain.models.NoteConstants.C4
import com.tyom.domain.models.NoteConstants.C5
import com.tyom.domain.models.NoteConstants.C6
import com.tyom.domain.models.NoteConstants.C7
import com.tyom.domain.models.NoteConstants.C8
import com.tyom.domain.models.NoteConstants.Cd1_Db1
import com.tyom.domain.models.NoteConstants.Cd3_Db3
import com.tyom.domain.models.NoteConstants.Cd4_Db4
import com.tyom.domain.models.NoteConstants.Cd5_Db5
import com.tyom.domain.models.NoteConstants.Cd6_Db6
import com.tyom.domain.models.NoteConstants.Cd7_Db7
import com.tyom.domain.models.NoteConstants.D1
import com.tyom.domain.models.NoteConstants.D3
import com.tyom.domain.models.NoteConstants.D4
import com.tyom.domain.models.NoteConstants.D5
import com.tyom.domain.models.NoteConstants.D6
import com.tyom.domain.models.NoteConstants.D7
import com.tyom.domain.models.NoteConstants.DEFAULT
import com.tyom.domain.models.NoteConstants.Dd1_Eb1
import com.tyom.domain.models.NoteConstants.Dd3_Eb3
import com.tyom.domain.models.NoteConstants.Dd4_Eb4
import com.tyom.domain.models.NoteConstants.Dd5_Eb5
import com.tyom.domain.models.NoteConstants.Dd6_Eb6
import com.tyom.domain.models.NoteConstants.Dd7_Eb7
import com.tyom.domain.models.NoteConstants.E1
import com.tyom.domain.models.NoteConstants.E3
import com.tyom.domain.models.NoteConstants.E4
import com.tyom.domain.models.NoteConstants.E5
import com.tyom.domain.models.NoteConstants.E6
import com.tyom.domain.models.NoteConstants.E7
import com.tyom.domain.models.NoteConstants.F1
import com.tyom.domain.models.NoteConstants.F3
import com.tyom.domain.models.NoteConstants.F4
import com.tyom.domain.models.NoteConstants.F5
import com.tyom.domain.models.NoteConstants.F6
import com.tyom.domain.models.NoteConstants.F7
import com.tyom.domain.models.NoteConstants.Fd1_Gb1
import com.tyom.domain.models.NoteConstants.Fd2_Gb2
import com.tyom.domain.models.NoteConstants.Fd3_Gb3
import com.tyom.domain.models.NoteConstants.Fd4_Gb4
import com.tyom.domain.models.NoteConstants.Fd5_Gb5
import com.tyom.domain.models.NoteConstants.Fd6_Gb6
import com.tyom.domain.models.NoteConstants.Fd7_Gb7
import com.tyom.domain.models.NoteConstants.G1
import com.tyom.domain.models.NoteConstants.G2
import com.tyom.domain.models.NoteConstants.G3
import com.tyom.domain.models.NoteConstants.G4
import com.tyom.domain.models.NoteConstants.G5
import com.tyom.domain.models.NoteConstants.G6
import com.tyom.domain.models.NoteConstants.G7
import com.tyom.domain.models.NoteConstants.Gd1_Ab1
import com.tyom.domain.models.NoteConstants.Gd2_Ab2
import com.tyom.domain.models.NoteConstants.Gd3_Ab3
import com.tyom.domain.models.NoteConstants.Gd4_Ab4
import com.tyom.domain.models.NoteConstants.Gd5_Ab5
import com.tyom.domain.models.NoteConstants.Gd6_Ab6
import com.tyom.domain.models.NoteConstants.Gd7_Ab7
import com.tyom.domain.models.NoteConstants.B1
import com.tyom.domain.models.NoteConstants.C2
import com.tyom.domain.models.NoteConstants.Cd2_Db2
import com.tyom.domain.models.NoteConstants.D2
import com.tyom.domain.models.NoteConstants.Dd2_Eb2
import com.tyom.domain.models.NoteConstants.E2
import com.tyom.domain.models.NoteConstants.F2

data class PianoConfiguration(
    val color: Color = Color.Black,
    val backgroundColor: Color = Color.White,

    val lineSpacing: Float = 23f,
    val lineCount: Int = 5,
    val noteCount: Int = 10,
    val firstBassNote: Int = B3,

    val noteCountWithPadding: Int = noteCount + 1,
    val lineSpacingDp: Dp = lineSpacing.dp,
    val halfLineSpacing: Float = lineSpacing / 2,
    val topPadding: Float = 0f,
    val strokeWidth: Float = lineSpacing / 5,
    val padding: Float = lineSpacing * 5,
    val paddingForBottomLine: Float = lineSpacing * 2,
    val notePaddingTop: Float = padding - (lineSpacing * 1.5f),
    val notePaddingBottom: Float = paddingForBottomLine - (lineSpacing * 1.5f),
    val widthFromStrokes: Float = lineSpacing * 31,
    val bassLinesStartX: Float = halfLineSpacing * 5 + padding,
    val topLinesStartX: Float = halfLineSpacing * 23 + padding,

    val trebleClefWidth: Int = (lineSpacing * 9).toInt(),
    val trebleClefHeight: Int = (lineSpacing * 12).toInt(),
    val trebleClefX: Float = topLinesStartX - lineSpacing * 2,
    val trebleClefY: Float = -lineSpacing * 3,
    val trebleClefOffset: Offset = Offset(trebleClefX, trebleClefY),

    val bassClefWidth: Int = (lineSpacing * 5).toInt(),
    val bassClefHeight: Int = (lineSpacing * 9).toInt(),
    val bassClefX: Float = bassLinesStartX,
    val bassClefY: Float = -lineSpacing,
    val bassClefOffset: Offset = Offset(bassClefX, bassClefY),

    val topNoteLinePadding: Float = lineSpacing * 1.7f,
    val bottomNoteLinePadding: Float = -lineSpacing * 0.2f,
    val noteWidth: Float = lineSpacing * 1.5f,

    val endEdgeX: Float = topLinesStartX + lineSpacing * 4,
    val endEdgeY: Float = 0f,
    val startEdgeX: Float = bassLinesStartX,
    val startEdgeY: Float = 0f,

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
