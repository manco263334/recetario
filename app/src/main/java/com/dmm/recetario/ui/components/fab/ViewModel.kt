package com.dmm.recetario.ui.components.fab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.service.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FABViewModel @Inject constructor (
    private val categoryService: CategoryService
): ViewModel() {
    val categories = categoryService
        .getAllCategories()
        .stateIn (
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}