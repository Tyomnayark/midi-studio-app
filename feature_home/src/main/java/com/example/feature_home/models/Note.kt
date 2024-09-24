package com.example.feature_home.models

data class Note(
    val value: Int,
    val isWhiteKey: Boolean
)

fun Int.toNote(): Note {
    return when (this) {
        21 -> Note(value = 0, isWhiteKey = true)   // A0
        22 -> Note(value = 0, isWhiteKey = false)  // A#0 / Bb0
        23 -> Note(value = 1, isWhiteKey = true)   // B0
        24 -> Note(value = 2, isWhiteKey = true)   // C1
        25 -> Note(value = 2, isWhiteKey = false)  // C#1 / Db1
        26 -> Note(value = 3, isWhiteKey = true)   // D1
        27 -> Note(value = 3, isWhiteKey = false)  // D#1 / Eb1
        28 -> Note(value = 4, isWhiteKey = true)   // E1
        29 -> Note(value = 5, isWhiteKey = true)   // F1
        30 -> Note(value = 5, isWhiteKey = false)  // F#1 / Gb1
        31 -> Note(value = 6, isWhiteKey = true)   // G1
        32 -> Note(value = 6, isWhiteKey = false)  // G#1 / Ab1
        33 -> Note(value = 7, isWhiteKey = true)   // A1
        34 -> Note(value = 7, isWhiteKey = false)  // A#1 / Bb1
        35 -> Note(value = 8, isWhiteKey = true)   // B1
        36 -> Note(value = 9, isWhiteKey = true)   // C2
        37 -> Note(value = 9, isWhiteKey = false)  // C#2 / Db2
        38 -> Note(value = 10, isWhiteKey = true)  // D2
        39 -> Note(value = 10, isWhiteKey = false) // D#2 / Eb2
        40 -> Note(value = 11, isWhiteKey = true)  // E2
        41 -> Note(value = 12, isWhiteKey = true)  // F2
        42 -> Note(value = 12, isWhiteKey = false) // F#2 / Gb2
        43 -> Note(value = 13, isWhiteKey = true)  // G2
        44 -> Note(value = 13, isWhiteKey = false) // G#2 / Ab2
        45 -> Note(value = 14, isWhiteKey = true)  // A2
        46 -> Note(value = 14, isWhiteKey = false) // A#2 / Bb2
        47 -> Note(value = 15, isWhiteKey = true)  // B2
        48 -> Note(value = 16, isWhiteKey = true)  // C3
        49 -> Note(value = 16, isWhiteKey = false) // C#3 / Db3
        50 -> Note(value = 17, isWhiteKey = true)  // D3
        51 -> Note(value = 17, isWhiteKey = false) // D#3 / Eb3
        52 -> Note(value = 18, isWhiteKey = true)  // E3
        53 -> Note(value = 19, isWhiteKey = true)  // F3
        54 -> Note(value = 19, isWhiteKey = false) // F#3 / Gb3
        55 -> Note(value = 20, isWhiteKey = true)  // G3
        56 -> Note(value = 20, isWhiteKey = false) // G#3 / Ab3
        57 -> Note(value = 21, isWhiteKey = true)  // A3
        58 -> Note(value = 21, isWhiteKey = false) // A#3 / Bb3
        59 -> Note(value = 22, isWhiteKey = true)  // B3
        60 -> Note(value = 23, isWhiteKey = true)  // C4 (Middle C)
        61 -> Note(value = 23, isWhiteKey = false) // C#4 / Db4
        62 -> Note(value = 24, isWhiteKey = true)  // D4
        63 -> Note(value = 24, isWhiteKey = false) // D#4 / Eb4
        64 -> Note(value = 25, isWhiteKey = true)  // E4
        65 -> Note(value = 26, isWhiteKey = true)  // F4
        66 -> Note(value = 26, isWhiteKey = false) // F#4 / Gb4
        67 -> Note(value = 27, isWhiteKey = true)  // G4
        68 -> Note(value = 27, isWhiteKey = false) // G#4 / Ab4
        69 -> Note(value = 28, isWhiteKey = true)  // A4
        70 -> Note(value = 28, isWhiteKey = false) // A#4 / Bb4
        71 -> Note(value = 29, isWhiteKey = true)  // B4
        72 -> Note(value = 30, isWhiteKey = true)  // C5
        73 -> Note(value = 30, isWhiteKey = false) // C#5 / Db5
        74 -> Note(value = 31, isWhiteKey = true)  // D5
        75 -> Note(value = 31, isWhiteKey = false) // D#5 / Eb5
        76 -> Note(value = 32, isWhiteKey = true)  // E5
        77 -> Note(value = 33, isWhiteKey = true)  // F5
        78 -> Note(value = 33, isWhiteKey = false) // F#5 / Gb5
        79 -> Note(value = 34, isWhiteKey = true)  // G5
        80 -> Note(value = 34, isWhiteKey = false) // G#5 / Ab5
        81 -> Note(value = 35, isWhiteKey = true)  // A5
        82 -> Note(value = 35, isWhiteKey = false) // A#5 / Bb5
        83 -> Note(value = 36, isWhiteKey = true)  // B5
        84 -> Note(value = 37, isWhiteKey = true)  // C6
        85 -> Note(value = 37, isWhiteKey = false) // C#6 / Db6
        86 -> Note(value = 38, isWhiteKey = true)  // D6
        87 -> Note(value = 38, isWhiteKey = false) // D#6 / Eb6
        88 -> Note(value = 39, isWhiteKey = true)  // E6
        89 -> Note(value = 40, isWhiteKey = true)  // F6
        90 -> Note(value = 40, isWhiteKey = false) // F#6 / Gb6
        91 -> Note(value = 41, isWhiteKey = true)  // G6
        92 -> Note(value = 41, isWhiteKey = false) // G#6 / Ab6
        93 -> Note(value = 42, isWhiteKey = true)  // A6
        94 -> Note(value = 42, isWhiteKey = false) // A#6 / Bb6
        95 -> Note(value = 43, isWhiteKey = true)  // B6
        96 -> Note(value = 44, isWhiteKey = true)  // C7
        97 -> Note(value = 44, isWhiteKey = false) // C#7 / Db7
        98 -> Note(value = 45, isWhiteKey = true)  // D7
        99 -> Note(value = 45, isWhiteKey = false) // D#7 / Eb7
        100 -> Note(value = 46, isWhiteKey = true) // E7
        101 -> Note(value = 47, isWhiteKey = true) // F7
        102 -> Note(value = 47, isWhiteKey = false) // F#7 / Gb7
        103 -> Note(value = 48, isWhiteKey = true) // G7
        104 -> Note(value = 48, isWhiteKey = false) // G#7 / Ab7
        105 -> Note(value = 49, isWhiteKey = true) // A7
        106 -> Note(value = 49, isWhiteKey = false) // A#7 / Bb7
        107 -> Note(value = 50, isWhiteKey = true) // B7
        108 -> Note(value = 51, isWhiteKey = true) // C8
        else -> Note(value = 1, isWhiteKey = true) // Default
    }
}
