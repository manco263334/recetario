package com.dmm.recetario.ui.category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.service.CategoryService
import com.dmm.recetario.data.service.RecipeService
import com.dmm.recetario.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

@HiltViewModel
class CategoryViewModel @Inject constructor (
    private val categoryService: CategoryService,
    private val recipeService: RecipeService
): ViewModel() {
    private val _selectedCategoryId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes: StateFlow<List<Recipe>> = _selectedCategoryId
        .flatMapLatest { categoryId ->
            if (categoryId == null) {
                flowOf(emptyList())
            } else {
                categoryService.getRecipes(categoryId)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        sync()
    }

    fun loadRecipes(categoryId: String) {
        _selectedCategoryId.value = categoryId
    }

    fun sync() {
        viewModelScope.launch {
            recipeService.syncRecipes()
            categoryService.syncCategories()
        }
    }
}