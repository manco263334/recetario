package com.dmm.recetario.core.utils.extension

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey): Boolean {
    return this.add(screen)
}

fun NavBackStack<NavKey>.back(): NavKey? {
    if (this.isEmpty()) return null

    return this.removeLastOrNull()
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if (this.isEmpty()) return

    if (targetScreen !in this) return

    while (this.isNotEmpty() && this.last() != targetScreen) {
        this.removeLastOrNull()
    }
}