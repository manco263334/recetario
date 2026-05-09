package com.dmm.recetario.data.model.dto

data class CategoryDTO (
    val id: String,
    val name: String,
    val icon: String?,

    val recipes: List<Map<String, Any?>>?
)