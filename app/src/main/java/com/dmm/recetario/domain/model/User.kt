package com.dmm.recetario.domain.model

data class User (
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val username: String?,
    val icon: String?,

    val recipes: List<String>?
)