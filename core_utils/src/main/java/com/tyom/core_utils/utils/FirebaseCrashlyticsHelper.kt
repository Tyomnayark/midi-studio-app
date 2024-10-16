package com.tyom.core_utils.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics

fun sendExceptionToFirebase(fileName: String, exception: Exception) {
    FirebaseCrashlytics.getInstance().log("Exception in class: " + fileName);
    FirebaseCrashlytics.getInstance().recordException(exception)
}
