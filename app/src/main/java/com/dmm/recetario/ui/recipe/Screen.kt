package com.dmm.recetario.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.recetario.core.utils.extension.isNeitherNullNorAnonymous
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.ui.components.Toolbar
import com.dmm.recetario.ui.components.WelcomeHeader
import com.dmm.recetario.ui.components.drawer.DrawerContent
import com.dmm.recetario.ui.components.fab.FAB
import com.dmm.recetario.ui.components.refresher.PullToRefresh

@Composable
fun RecipeScreen (
    recipeId: String,
    user: User?,
    onSettingsClick: (user: User?) -> Unit,
    onLogOutSuccess: () -> Unit,
    onHomeClick: () -> Unit,
    onCompleteForm: () -> Unit,
    viewModel: RecipeViewModel = hiltViewModel(),
) {
    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val recipe by viewModel.recipe.collectAsStateWithLifecycle()

    ModalNavigationDrawer (
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            DrawerContent (
                scaffoldState = drawerState,
                user = user,
                onSettingsClick = onSettingsClick,
                onLogOutSuccess = onLogOutSuccess,
                onHomeClick =  onHomeClick
            )
        }
    ) {
        Scaffold (
            topBar = {
                Toolbar (
                    scaffoldState = drawerState
                ) {
                    WelcomeHeader(user)
                }
            },
            content = { paddingValues ->
                RecipeContent (
                    paddingValues = paddingValues,
                    recipe = recipe,
                    onRefresh = viewModel::refresh
                )
            },
            floatingActionButton = {
                if (user.isNeitherNullNorAnonymous()) {
                    FAB(onCompleteForm = onCompleteForm)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}

@Composable
private fun RecipeContent (
    paddingValues: PaddingValues,
    recipe: Recipe?,
    onRefresh: suspend () -> Unit
) {
    PullToRefresh (
        onRefresh = onRefresh,
        modifier = Modifier.padding(paddingValues)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            recipe?.let {
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = it.name, style = MaterialTheme.typography.headlineMedium)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Tiempo total: ${it.totalTimeInMinutes} min")
                        Text(text = "Preparación: ${it.preparationTimeInMinutes} min")
                        Text(text = "Cocción: ${it.cookingTimeInMinutes} min")
                    }
                }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text (
                            text = "Ingredientes",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        it.ingredients.forEach { ingredient ->
                            Text(text = "* ${ingredient["quantity"] ?: "N/A"} de ${ingredient["name"] ?: "N/A"}")
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text (
                            text = "Pasos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        var index = 0
                        it.steps.forEach { step ->
                            Text(text = "${index++}. $step")
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Calificación: ${it.stars} ⭐", style = MaterialTheme.typography.titleLarge)
                    }
                }
            } ?: run {
                Text(text = "No hay receta disponible", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}