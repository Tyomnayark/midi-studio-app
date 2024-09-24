package com.example.feature_home.constants

import com.example.feature_home.constants.NoteConstants.A0
import com.example.feature_home.constants.NoteConstants.A2
import com.example.feature_home.constants.NoteConstants.A3
import com.example.feature_home.constants.NoteConstants.A5
import com.example.feature_home.constants.NoteConstants.A6
import com.example.feature_home.constants.NoteConstants.A7
import com.example.feature_home.constants.NoteConstants.B0
import com.example.feature_home.constants.NoteConstants.B2
import com.example.feature_home.constants.NoteConstants.B3
import com.example.feature_home.constants.NoteConstants.B5
import com.example.feature_home.constants.NoteConstants.B6
import com.example.feature_home.constants.NoteConstants.B7
import com.example.feature_home.constants.NoteConstants.C1
import com.example.feature_home.constants.NoteConstants.C2
import com.example.feature_home.constants.NoteConstants.C4
import com.example.feature_home.constants.NoteConstants.C6
import com.example.feature_home.constants.NoteConstants.C7
import com.example.feature_home.constants.NoteConstants.C8
import com.example.feature_home.constants.NoteConstants.D1
import com.example.feature_home.constants.NoteConstants.D2
import com.example.feature_home.constants.NoteConstants.D6
import com.example.feature_home.constants.NoteConstants.D7
import com.example.feature_home.constants.NoteConstants.E1
import com.example.feature_home.constants.NoteConstants.E2
import com.example.feature_home.constants.NoteConstants.E6
import com.example.feature_home.constants.NoteConstants.E7
import com.example.feature_home.constants.NoteConstants.F1
import com.example.feature_home.constants.NoteConstants.F2
import com.example.feature_home.constants.NoteConstants.F6
import com.example.feature_home.constants.NoteConstants.F7
import com.example.feature_home.constants.NoteConstants.G1
import com.example.feature_home.constants.NoteConstants.G2
import com.example.feature_home.constants.NoteConstants.G6
import com.example.feature_home.constants.NoteConstants.G7

object NoteConstants {
    const val Ad0_Bb0 = 0
    const val Cd1_Db1 = 2
    const val Dd1_Eb1 = 3
    const val Fd1_Gb1 = 5
    const val Gd1_Ab1 = 6
    const val Ad1_Bb1 = 7
    const val Cd2_Db2 = 9
    const val Dd2_Eb2 = 10
    const val Fd2_Gb2 = 12
    const val Gd2_Ab2 = 13
    const val Ad2_Bb2 = 14
    const val Cd3_Db3 = 16
    const val Dd3_Eb3 = 17
    const val Fd3_Gb3 = 19
    const val Gd3_Ab3 = 20
    const val Ad3_Bb3 = 21
    const val Cd4_Db4 = 23
    const val Dd4_Eb4 = 24
    const val Fd4_Gb4 = 26
    const val Gd4_Ab4 = 27
    const val Ad4_Bb4 = 28
    const val Cd5_Db5 = 30
    const val Dd5_Eb5 = 31
    const val Fd5_Gb5 = 33
    const val Gd5_Ab5 = 34
    const val Ad5_Bb5 = 35
    const val Cd6_Db6 = 37
    const val Dd6_Eb6 = 38
    const val Fd6_Gb6 = 40
    const val Gd6_Ab6 = 41
    const val Ad6_Bb6 = 42
    const val Cd7_Db7 = 44
    const val Dd7_Eb7 = 45
    const val Fd7_Gb7 = 47
    const val Gd7_Ab7 = 48
    const val Ad7_Bb7 = 49


    const val A0 = 0
    const val B0 = 1
    const val C1 = 2
    const val D1 = 3
    const val E1 = 4
    const val F1 = 5
    const val G1 = 6
    const val A1 = 7
    const val B1 = 8
    const val C2 = 9
    const val D2 = 10
    const val E2 = 11
    const val F2 = 12
    const val G2 = 13
    const val A2 = 14
    const val B2 = 15
    const val C3 = 16
    const val D3 = 17
    const val E3 = 18
    const val F3 = 19
    const val G3 = 20
    const val A3 = 21
    const val B3 = 22
    const val C4 = 23 // Middle
    const val D4 = 24
    const val E4 = 25
    const val F4 = 26
    const val G4 = 27
    const val A4 = 28
    const val B4 = 29
    const val C5 = 30
    const val D5 = 31
    const val E5 = 32
    const val F5 = 33
    const val G5 = 34
    const val A5 = 35
    const val B5 = 36
    const val C6 = 37
    const val D6 = 38
    const val E6 = 39
    const val F6 = 40
    const val G6 = 41
    const val A6 = 42
    const val B6 = 43
    const val C7 = 44
    const val D7 = 45
    const val E7 = 46
    const val F7 = 47
    const val G7 = 48
    const val A7 = 49
    const val B7 = 50
    const val C8 = 51

    const val DEFAULT = 52
}

val NeedLineNotesMap = mapOf(
    //top lines
    B3 to 1,
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
)

val BottomLineNotesList = listOf(
    B3, C4, E2, D2, C2, B2, A2, G1, F1, E1, D1, C1, B0, A0
)