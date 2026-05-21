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

    fun sync() {
        viewModelScope.launch {
            categoryService.syncCategories()
        }
    }
}