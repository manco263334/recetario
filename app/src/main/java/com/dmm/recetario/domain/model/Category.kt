package com.dmm.recetario.domain.model

data class Category (
    val id: String,
    val name: String,
    val icon: String?,

    val recipes: List<String>?
)