package com.dmm.recetario.data.remote.dto

data class UserDTO (
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val username: String?,
    val icon: String?,

    val recipes: List<Map<String, Any?>>?
)