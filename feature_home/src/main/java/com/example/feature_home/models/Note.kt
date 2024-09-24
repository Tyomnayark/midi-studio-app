package com.example.feature_home.models

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

data class Note(
    val value: Int,
    val isWhiteKey: Boolean
)

fun Int.toNote(): Note {
    return when (this) {
        21 -> Note(value = A0, isWhiteKey = true)   // A0
        22 -> Note(value = Ad0_Bb0, isWhiteKey = false)  // A#0 / Bb0
        23 -> Note(value = B0, isWhiteKey = true)   // B0
        24 -> Note(value = C1, isWhiteKey = true)   // C1
        25 -> Note(value = Cd1_Db1, isWhiteKey = false)  // C#1 / Db1
        26 -> Note(value = D1, isWhiteKey = true)   // D1
        27 -> Note(value = Dd1_Eb1, isWhiteKey = false)  // D#1 / Eb1
        28 -> Note(value = E1, isWhiteKey = true)   // E1
        29 -> Note(value = F1, isWhiteKey = true)   // F1
        30 -> Note(value = Fd1_Gb1, isWhiteKey = false)  // F#1 / Gb1
        31 -> Note(value = G1, isWhiteKey = true)   // G1
        32 -> Note(value = Gd1_Ab1, isWhiteKey = false)  // G#1 / Ab1
        33 -> Note(value = A1, isWhiteKey = true)   // A1
        34 -> Note(value = Ad1_Bb1, isWhiteKey = false)  // A#1 / Bb1
        35 -> Note(value = B1, isWhiteKey = true)   // B1
        36 -> Note(value = C2, isWhiteKey = true)   // C2
        37 -> Note(value = Cd2_Db2, isWhiteKey = false)  // C#2 / Db2
        38 -> Note(value = D2, isWhiteKey = true)  // D2
        39 -> Note(value = Dd2_Eb2, isWhiteKey = false) // D#2 / Eb2
        40 -> Note(value = E2, isWhiteKey = true)  // E2
        41 -> Note(value = F2, isWhiteKey = true)  // F2
        42 -> Note(value = Fd2_Gb2, isWhiteKey = false) // F#2 / Gb2
        43 -> Note(value = G2, isWhiteKey = true)  // G2
        44 -> Note(value = Gd2_Ab2, isWhiteKey = false) // G#2 / Ab2
        45 -> Note(value = A2, isWhiteKey = true)  // A2
        46 -> Note(value = Ad2_Bb2, isWhiteKey = false) // A#2 / Bb2
        47 -> Note(value = B2, isWhiteKey = true)  // B2
        48 -> Note(value = C3, isWhiteKey = true)  // C3
        49 -> Note(value = Cd3_Db3, isWhiteKey = false) // C#3 / Db3
        50 -> Note(value = D3, isWhiteKey = true)  // D3
        51 -> Note(value = Dd3_Eb3, isWhiteKey = false) // D#3 / Eb3
        52 -> Note(value = E3, isWhiteKey = true)  // E3
        53 -> Note(value = F3, isWhiteKey = true)  // F3
        54 -> Note(value = Fd3_Gb3, isWhiteKey = false) // F#3 / Gb3
        55 -> Note(value = G3, isWhiteKey = true)  // G3
        56 -> Note(value = Gd3_Ab3, isWhiteKey = false) // G#3 / Ab3
        57 -> Note(value = A3, isWhiteKey = true)  // A3
        58 -> Note(value = Ad3_Bb3, isWhiteKey = false) // A#3 / Bb3
        59 -> Note(value = B3, isWhiteKey = true)  // B3
        60 -> Note(value = C4, isWhiteKey = true)  // C4 (Middle C)
        61 -> Note(value = Cd4_Db4, isWhiteKey = false) // C#4 / Db4
        62 -> Note(value = D4, isWhiteKey = true)  // D4
        63 -> Note(value = Dd4_Eb4, isWhiteKey = false) // D#4 / Eb4
        64 -> Note(value = E4, isWhiteKey = true)  // E4
        65 -> Note(value = F4, isWhiteKey = true)  // F4
        66 -> Note(value = Fd4_Gb4, isWhiteKey = false) // F#4 / Gb4
        67 -> Note(value = G4, isWhiteKey = true)  // G4
        68 -> Note(value = Gd4_Ab4, isWhiteKey = false) // G#4 / Ab4
        69 -> Note(value = A4, isWhiteKey = true)  // A4
        70 -> Note(value = Ad4_Bb4, isWhiteKey = false) // A#4 / Bb4
        71 -> Note(value = B4, isWhiteKey = true)  // B4
        72 -> Note(value = C5, isWhiteKey = true)  // C5
        73 -> Note(value = Cd5_Db5, isWhiteKey = false) // C#5 / Db5
        74 -> Note(value = D5, isWhiteKey = true)  // D5
        75 -> Note(value = Dd5_Eb5, isWhiteKey = false) // D#5 / Eb5
        76 -> Note(value = E5, isWhiteKey = true)  // E5
        77 -> Note(value = F5, isWhiteKey = true)  // F5
        78 -> Note(value = Fd5_Gb5, isWhiteKey = false) // F#5 / Gb5
        79 -> Note(value = G5, isWhiteKey = true)  // G5
        80 -> Note(value = Gd5_Ab5, isWhiteKey = false) // G#5 / Ab5
        81 -> Note(value = A5, isWhiteKey = true)  // A5
        82 -> Note(value = Ad5_Bb5, isWhiteKey = false) // A#5 / Bb5
        83 -> Note(value = B5, isWhiteKey = true)  // B5
        84 -> Note(value = C6, isWhiteKey = true)  // C6
        85 -> Note(value = Cd6_Db6, isWhiteKey = false) // C#6 / Db6
        86 -> Note(value = D6, isWhiteKey = true)  // D6
        87 -> Note(value = Dd6_Eb6, isWhiteKey = false) // D#6 / Eb6
        88 -> Note(value = E6, isWhiteKey = true)  // E6
        89 -> Note(value = F6, isWhiteKey = true)  // F6
        90 -> Note(value = Fd6_Gb6, isWhiteKey = false) // F#6 / Gb6
        91 -> Note(value = G6, isWhiteKey = true)  // G6
        92 -> Note(value = Gd6_Ab6, isWhiteKey = false) // G#6 / Ab6
        93 -> Note(value = A6, isWhiteKey = true)  // A6
        94 -> Note(value = Ad6_Bb6, isWhiteKey = false) // A#6 / Bb6
        95 -> Note(value = B6, isWhiteKey = true)  // B6
        96 -> Note(value = C7, isWhiteKey = true)  // C7
        97 -> Note(value = Cd7_Db7, isWhiteKey = false) // C#7 / Db7
        98 -> Note(value = D7, isWhiteKey = true)  // D7
        99 -> Note(value = Dd7_Eb7, isWhiteKey = false) // D#7 / Eb7
        100 -> Note(value = E7, isWhiteKey = true) // E7
        101 -> Note(value = F7, isWhiteKey = true) // F7
        102 -> Note(value = Fd7_Gb7, isWhiteKey = false) // F#7 / Gb7
        103 -> Note(value = G7, isWhiteKey = true) // G7
        104 -> Note(value = Gd7_Ab7, isWhiteKey = false) // G#7 / Ab7
        105 -> Note(value = A7, isWhiteKey = true) // A7
        106 -> Note(value = Ad7_Bb7, isWhiteKey = false) // A#7 / Bb7
        107 -> Note(value = B7, isWhiteKey = true) // B7
        108 -> Note(value = C8, isWhiteKey = true) // C8
        else -> Note(value = DEFAULT, isWhiteKey = true) // Default
    }
}