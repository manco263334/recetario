package com.dmm.recetario.ui.components.fab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.service.CategoryService
import com.dmm.recetario.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FABViewModel @Inject constructor(
    private val categoryService: CategoryService
): ViewModel() {
    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categories = categoryService.getAllCategories()
        }
    }
}