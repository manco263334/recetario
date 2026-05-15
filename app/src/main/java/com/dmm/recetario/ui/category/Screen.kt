package com.dmm.recetario.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dmm.recetario.core.utils.extension.isNotAnonymous
import com.dmm.recetario.domain.model.AnonymousUser
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.ui.components.Toolbar
import com.dmm.recetario.ui.components.WellnessCard
import com.dmm.recetario.ui.components.drawer.DrawerContent
import com.dmm.recetario.ui.components.fab.FAB

@Composable
fun CategoryScreen (
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    onSettingsClick: (user: User?) -> Unit,
    onLogOutSuccess: () -> Unit,
    onHomeClick: () -> Unit,
    onCompleteForm: () -> Unit,
    user: User?,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer (
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            DrawerContent (
                scaffoldState = drawerState,
                user = user,
                onSettingsClick = onSettingsClick,
                onLogOutSuccess = onLogOutSuccess,
                onHomeClick = onHomeClick
            )
        }
    ){
        Scaffold (
            topBar = {
                Toolbar (
                    scaffoldState = drawerState
                ) { }
            },
            content = { paddingValues ->
                CategoryContent (
                    paddingValues = paddingValues,
                    recipes = recipes,
                    onRecipeClick = onRecipeClick
                )
            },
            floatingActionButton = {
                if (user.isNotAnonymous()) {
                    FAB(onCompleteForm = onCompleteForm)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}

@Composable
private fun CategoryContent (
    paddingValues: PaddingValues,
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (recipes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            val columns = 2
            for (i in recipes.indices step columns) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (j in 0 until columns) {
                        if (i + j < recipes.size) {
                            val recipe = recipes[i + j]
                            WellnessCard (
                                title = recipe.name,
                                onClick = { recipe.let(onRecipeClick) },
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color.White
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            recipes.forEach { recipe ->
                WellnessCard (
                    title = recipe.name,
                    onClick = { recipe.let(onRecipeClick) },
                )
            }
        } else {

        }
    }
}