package com.dmm.recetario.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
object Settings

@Serializable
data class Category (
    val id: String
)

@Serializable
data class Recipe (
    val id: String
)