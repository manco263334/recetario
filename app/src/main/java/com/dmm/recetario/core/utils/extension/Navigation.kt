package com.dmm.recetario.core.utils.extension

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun <T: NavKey> NavBackStack<T>.navigateTo(screen: T): Boolean {
    return this.add(screen)
}

fun <T: NavKey> NavBackStack<T>.back(): T? {
    if (this.isEmpty()) return null

    return this.removeLastOrNull()
}

fun <T: NavKey> NavBackStack<T>.backTo(targetScreen: T) {
    if (this.isEmpty()) return

    if (targetScreen !in this) return

    while (this.isNotEmpty() && this.last() != targetScreen) {
        this.removeLastOrNull()
    }
}