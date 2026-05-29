package com.dmm.recetario.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.service.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val categoryService: CategoryService
): ViewModel() {
    val categories = categoryService
        .getAllCategories()
        .stateIn (
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        sync()
    }

    fun sync (
        page: Int = 0,
        size: Int = 10
    ) {
        viewModelScope.launch {
            categoryService.syncCategories (
                page = page,
                size = size,
                withRecipes = true
            )
        }
    }

    suspend fun refresh() {
        val result = categoryService.syncCategories(withRecipes = true)

        if (!result) {
            throw Exception("Error sincronizando las categorías")
        }
    }
}