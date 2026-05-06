package com.dmm.recetario.utils.mapper

import com.dmm.recetario.data.model.dto.RecipeDTO
import com.dmm.recetario.domain.model.Recipe

fun RecipeDTO.toDomain(): Recipe {
    val creator = this.creator?.get("id")?.toString()
    val categories = this.categories?.mapNotNull {
        it["id"]?.toString()
    }

    return Recipe (
        id = this.id,
        name = this.name,
        persons = this.persons,
        ingredients = this.ingredients,
        steps = this.steps,
        totalTimeInMinutes = this.totalTimeInMinutes,
        cookingTimeInMinutes = this.cookingTimeInMinutes,
        preparationTimeInMinutes = this.preparationTimeInMinutes,
        stars = this.stars,
        icon = this.icon,

        creator = creator,
        categories = categories
    )
}