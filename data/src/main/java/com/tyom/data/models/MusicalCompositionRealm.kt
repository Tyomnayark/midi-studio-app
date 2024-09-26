package com.tyom.data.models

import com.tyom.core_ui.constants.NoteConstants.DEFAULT
import com.tyom.core_utils.extensions.empty
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class MusicalCompositionRealm: RealmObject {
    var title: String = String.empty()
    var notePairRealms: RealmList<NotePairRealm> = realmListOf()
}

class NotePairRealm : RealmObject {
    var notes: RealmList<NoteRealm> = realmListOf()
    var orderNumber: Int = 0
}

class NoteRealm : RealmObject {
    var value: Int = DEFAULT
    var isWhiteKey: Boolean = false
}