package com.tyom.utils

import android.view.animation.Animation

/**
 * [setActionOnAnimationEnd] используется в диалогах,
 * где нет наследования от [com.blackhub.bronline.game.common.BaseFragment].
 * Выполняет действие после завершения анимации.
 * Лучше использовать с [com.blackhub.bronline.game.common.TimeChecker]
 */
fun Animation.setActionOnAnimationEnd(action: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            action()
        }

        override fun onAnimationRepeat(animation: Animation?) {}
    })
}

/**
 * [setActionOnAnimationStart] используется в диалогах,
 * где нет наследования от [com.blackhub.bronline.game.common.BaseFragment].
 * Выполняет действие при старте анимации.
 */
fun Animation.setActionOnAnimationStart(action: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            action()
        }

        override fun onAnimationEnd(animation: Animation?) {}

        override fun onAnimationRepeat(animation: Animation?) {}
    })
}

fun Any.empty() = ""

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null