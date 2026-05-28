package com.dmm.recetario.core.jwt

fun isTokenExpired(token: String): Boolean {
    if (token.isBlank()) return true

    return false
}