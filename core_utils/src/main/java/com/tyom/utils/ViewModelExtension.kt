package com.tyom.utils

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ViewModel.launchOnIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchOn(Dispatchers.IO, start, block)
}

fun ViewModel.launchOnDefault(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchOn(Dispatchers.Default, start, block)
}

fun ViewModel.launchOnMain(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchOn(Dispatchers.Main, start, block)
}

fun <T> ViewModel.asyncOnIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return asyncOn(Dispatchers.IO, start, block)
}

fun <T> ViewModel.asyncOnDefault(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return asyncOn(Dispatchers.Default, start, block)
}

fun <T> ViewModel.asyncOnMain(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return asyncOn(Dispatchers.Main, start, block)
}

suspend fun <T> doOnDefault(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(dispatcher) {
        block()
    }
}

suspend fun <T> doOnIO(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(dispatcher) {
        block()
    }
}

suspend fun <T> doOnMain(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(dispatcher) {
        block()
    }
}

private fun ViewModel.launchOn(
    dispatcher: CoroutineDispatcher,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(dispatcher, start, block)
}

private fun <T> ViewModel.asyncOn(
    dispatcher: CoroutineDispatcher,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return viewModelScope.async(dispatcher, start, block)
}

fun <T> SavedStateHandle.getNotNull(key: String): T {
    return this.get<T>(key) ?: throw NullPointerException("$key doesn't exist in SavedStateHandler")
}
