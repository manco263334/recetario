package com.dmm.recetario.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.dmm.recetario.core.utils.extension.back
import com.dmm.recetario.core.utils.extension.backTo
import com.dmm.recetario.core.utils.extension.navigateTo
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.ui.auth.login.LoginScreen
import com.dmm.recetario.ui.auth.register.RegisterScreen
import com.dmm.recetario.ui.home.HomeScreen

@Composable
fun AppNavigation (
    backStack: NavBackStack<NavKey>,
    user: User?,
    modifier: Modifier = Modifier,
) {
    NavDisplay (
        backStack = backStack,
        modifier = modifier,
        onBack = backStack::back,
        entryDecorators = listOf (
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Routes.Login> {
                LoginScreen (
                    onNavigateToHome = {
                        backStack.clear()
                        backStack.navigateTo(Routes.Home)
                    },
                    onNavigateToRegister = {
                        backStack.navigateTo(Routes.Register)
                    }
                )
            }

            entry<Routes.Register> {
                RegisterScreen (
                    onNavigateToHome = {
                        backStack.clear()
                        backStack.navigateTo(Routes.Home)
                    },
                    onNavigateToLogin = {
                        backStack.backTo(Routes.Login)
                    }
                )
            }

            entry<Routes.Home> {
                HomeScreen (
                    onCategoryClick = {
                        backStack.navigateTo(Routes.Category(it.id))
                    },
                    onSettingsClick = {
                        backStack.navigateTo(Routes.Settings)
                    },
                    onLogOutSuccess = {
                        backStack.clear()
                        backStack.navigateTo(Routes.Login)
                    },
                    onCompleteForm = {
                        backStack.backTo(Routes.Home)
                    },
                    user = user
                )
            }

            entry<Routes.Category> {

            }

            entry<Routes.Settings> {

            }
        },
        transitionSpec = {
            slideInHorizontally (
                initialOffsetX = { it },
                animationSpec = tween(250)
            ) togetherWith slideOutHorizontally (
                targetOffsetX = { -it },
                animationSpec = tween(250)
            )
        },
        popTransitionSpec = {
            slideInHorizontally (
                initialOffsetX = { -it },
                animationSpec = tween(250)
            ) togetherWith slideOutHorizontally (
                targetOffsetX = { it },
                animationSpec = tween(250)
            )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally (
                initialOffsetX = { -it },
                animationSpec = tween(250)
            ) togetherWith slideOutHorizontally (
                targetOffsetX = { it },
                animationSpec = tween(250)
            )
        }
    )
}