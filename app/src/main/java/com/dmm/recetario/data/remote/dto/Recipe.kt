package com.dmm.recetario.data.remote.dto

data class RecipeDTO (
    val id: String,
    val name: String,
    val persons: Int,
    val ingredients: List<Map<String, String>>,
    val steps: List<String>,
    val totalTimeInMinutes: Int,
    val cookingTimeInMinutes: Int,
    val preparationTimeInMinutes: Int,
    val stars: Int,
    val icon: String?,

    val creator: Map<String, Any?>?,
    val categories: List<Map<String, Any?>>?
)