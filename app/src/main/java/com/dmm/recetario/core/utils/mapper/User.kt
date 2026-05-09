package com.dmm.recetario.core.utils.mapper

import com.dmm.recetario.data.model.dto.UserDTO
import com.dmm.recetario.domain.model.User

fun UserDTO.toDomain(): User {
    val recipes = this.recipes?.mapNotNull {
        it["id"]?.toString()
    }

    return User (
        id = this.id,
        name = this.name,
        email = this.email,
        role = this.role,
        phone = this.phone,
        username = this.username,
        icon = this.icon,

        recipes = recipes
    )
}