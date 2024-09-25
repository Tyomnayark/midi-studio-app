package com.tyom.data.models

import com.tyom.core_utils.extensions.empty
import io.realm.kotlin.types.RealmObject

class MusicalComposition: RealmObject {
    var title: String = String.empty()
//    var notes: List<Pair<List<Note>, Int>> = emptyList()
}