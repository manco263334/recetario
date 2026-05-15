package com.dmm.recetario.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dmm.recetario.core.utils.extension.isNotAnonymous
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.ui.auth.login.LoginError
import com.dmm.recetario.ui.components.CookingLoadingScreen
import com.dmm.recetario.ui.components.drawer.DrawerContent
import com.dmm.recetario.ui.components.Toolbar
import com.dmm.recetario.ui.components.WellnessCard
import com.dmm.recetario.ui.components.fab.FAB
import kotlinx.coroutines.launch

@Composable
fun HomeScreen (
    onCategoryClick: (Category) -> Unit,
    onSettingsClick: (User?) -> Unit,
    onLogOutSuccess: () -> Unit,
    onCompleteForm: () -> Unit,
    user: User?,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    when (state) {
        is HomeUiState.Success -> {
            ModalNavigationDrawer (
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent (
                        scaffoldState = drawerState,
                        user = user,
                        onSettingsClick = onSettingsClick,
                        onLogOutSuccess = onLogOutSuccess,
                        onHomeClick = { scope.launch { drawerState.close() } },
                    )
                }
            ) {
                Scaffold (
                    topBar = {
                        Toolbar (
                            scaffoldState = drawerState,
                            modifier = Modifier,
                        ) {
                            WelcomeHeader(user)
                        }
                    },
                    floatingActionButton = {
                        if (user.isNotAnonymous()) {
                            FAB(onCompleteForm = onCompleteForm)
                        }
                    }
                ) { paddingValues ->
                    HomeContent (
                        paddingValues = paddingValues,
                        categories = state.categories,
                        onCategoryClick = onCategoryClick
                    )
                }
            }
        }
        is HomeUiState.Error -> {
            LoginError(message = state.message, onRetry = viewModel::loadHomeData)
        }
        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CookingLoadingScreen()
            }
        }
    }
}

@Composable
private fun HomeContent (
    paddingValues: PaddingValues,
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Text (
                text = if (categories.isNotEmpty()) "Categorías disponibles" else "No hay categorías",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }

        items(categories) { category ->
            WellnessCard (
                title = category.name,
                onClick = { onCategoryClick(category) },
                backgroundColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun WelcomeHeader(user: User?) {
    var text = "Bienvenido, "
    text += user?.name ?: "Invitado"

    user?.username?.also {
        text += " ($it)"
    }

    Text(text, fontWeight = FontWeight.Bold)
}