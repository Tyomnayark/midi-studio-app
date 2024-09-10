package com.tyom.feature_main.utils

import com.tyom.feature_main.constants.PianoConstants.BLACK_KEY_TYPE
import com.tyom.feature_main.constants.PianoConstants.WHITE_KEY_TYPE

fun Int.toPianoPair(): Pair<Int, Int> {
    return when (this) {
        21 -> 0 to WHITE_KEY_TYPE  // A0
        22 -> 0 to BLACK_KEY_TYPE  // A#0 / Bb0
        23 -> 1 to WHITE_KEY_TYPE  // B0
        24 -> 2 to WHITE_KEY_TYPE  // C1
        25 -> 1 to BLACK_KEY_TYPE  // C#1 / Db1
        26 -> 3 to WHITE_KEY_TYPE  // D1
        27 -> 2 to BLACK_KEY_TYPE  // D#1 / Eb1
        28 -> 4 to WHITE_KEY_TYPE  // E1
        29 -> 5 to WHITE_KEY_TYPE  // F1
        30 -> 3 to BLACK_KEY_TYPE  // F#1 / Gb1
        31 -> 6 to WHITE_KEY_TYPE  // G1
        32 -> 4 to BLACK_KEY_TYPE  // G#1 / Ab1
        33 -> 7 to WHITE_KEY_TYPE  // A1
        34 -> 5 to BLACK_KEY_TYPE  // A#1 / Bb1
        35 -> 8 to WHITE_KEY_TYPE  // B1
        36 -> 9 to WHITE_KEY_TYPE // C2
        37 -> 6 to BLACK_KEY_TYPE  // C#2 / Db2
        38 -> 10 to WHITE_KEY_TYPE // D2
        39 -> 7 to BLACK_KEY_TYPE  // D#2 / Eb2
        40 -> 11 to WHITE_KEY_TYPE // E2
        41 -> 12 to WHITE_KEY_TYPE // F2
        42 -> 8 to BLACK_KEY_TYPE  // F#2 / Gb2
        43 -> 13 to WHITE_KEY_TYPE // G2
        44 -> 9 to BLACK_KEY_TYPE // G#2 / Ab2
        45 -> 14 to WHITE_KEY_TYPE // A2
        46 -> 10 to BLACK_KEY_TYPE // A#2 / Bb2
        47 -> 15 to WHITE_KEY_TYPE // B2
        48 -> 16 to WHITE_KEY_TYPE // C3
        49 -> 11 to BLACK_KEY_TYPE // C#3 / Db3
        50 -> 17 to WHITE_KEY_TYPE // D3
        51 -> 12 to BLACK_KEY_TYPE // D#3 / Eb3
        52 -> 18 to WHITE_KEY_TYPE // E3
        53 -> 19 to WHITE_KEY_TYPE // F3
        54 -> 13 to BLACK_KEY_TYPE // F#3 / Gb3
        55 -> 20 to WHITE_KEY_TYPE // G3
        56 -> 14 to BLACK_KEY_TYPE // G#3 / Ab3
        57 -> 21 to WHITE_KEY_TYPE // A3
        58 -> 15 to BLACK_KEY_TYPE // A#3 / Bb3
        59 -> 22 to WHITE_KEY_TYPE // B3
        60 -> 23 to WHITE_KEY_TYPE // C4 (Middle C)
        61 -> 16 to BLACK_KEY_TYPE // C#4 / Db4
        62 -> 24 to WHITE_KEY_TYPE // D4
        63 -> 17 to BLACK_KEY_TYPE // D#4 / Eb4
        64 -> 25 to WHITE_KEY_TYPE // E4
        65 -> 26 to WHITE_KEY_TYPE // F4
        66 -> 18 to BLACK_KEY_TYPE // F#4 / Gb4
        67 -> 27 to WHITE_KEY_TYPE // G4
        68 -> 19 to BLACK_KEY_TYPE // G#4 / Ab4
        69 -> 28 to WHITE_KEY_TYPE // A4
        70 -> 20 to BLACK_KEY_TYPE // A#4 / Bb4
        71 -> 31 to WHITE_KEY_TYPE // B4
        72 -> 30 to WHITE_KEY_TYPE // C5
        73 -> 21 to BLACK_KEY_TYPE // C#5 / Db5
        74 -> 31 to WHITE_KEY_TYPE // D5
        75 -> 22 to BLACK_KEY_TYPE // D#5 / Eb5
        76 -> 32 to WHITE_KEY_TYPE // E5
        77 -> 33 to WHITE_KEY_TYPE // F5
        78 -> 23 to BLACK_KEY_TYPE // F#5 / Gb5
        79 -> 34 to WHITE_KEY_TYPE // G5
        80 -> 24 to BLACK_KEY_TYPE // G#5 / Ab5
        81 -> 35 to WHITE_KEY_TYPE // A5
        82 -> 25 to BLACK_KEY_TYPE // A#5 / Bb5
        83 -> 36 to WHITE_KEY_TYPE // B5
        84 -> 37 to WHITE_KEY_TYPE // C6
        85 -> 26 to BLACK_KEY_TYPE // C#6 / Db6
        86 -> 38 to WHITE_KEY_TYPE // D6
        87 -> 27 to BLACK_KEY_TYPE // D#6 / Eb6
        88 -> 39 to WHITE_KEY_TYPE // E6
        89 -> 40 to WHITE_KEY_TYPE // F6
        90 -> 28 to BLACK_KEY_TYPE // F#6 / Gb6
        91 -> 41 to WHITE_KEY_TYPE // G6
        92 -> 29 to BLACK_KEY_TYPE // G#6 / Ab6
        93 -> 42 to WHITE_KEY_TYPE // A6
        94 -> 30 to BLACK_KEY_TYPE // A#6 / Bb6
        95 -> 43 to WHITE_KEY_TYPE // B6
        96 -> 44 to WHITE_KEY_TYPE // C7
        97 -> 31 to BLACK_KEY_TYPE // C#7 / Db7
        98 -> 45 to WHITE_KEY_TYPE // D7
        99 -> 32 to BLACK_KEY_TYPE // D#7 / Eb7
        100 -> 46 to WHITE_KEY_TYPE // E7
        101 -> 47 to WHITE_KEY_TYPE // F7
        102 -> 33 to BLACK_KEY_TYPE // F#7 / Gb7
        103 -> 48 to WHITE_KEY_TYPE // G7
        104 -> 34 to BLACK_KEY_TYPE // G#7 / Ab7
        105 -> 49 to WHITE_KEY_TYPE // A7
        106 -> 35 to BLACK_KEY_TYPE // A#7 / Bb7
        107 -> 50 to WHITE_KEY_TYPE // B7
        108 -> 51 to WHITE_KEY_TYPE // C8
        else -> 1 to WHITE_KEY_TYPE // Default, though this line should not be reached for valid inputs
    }
}
