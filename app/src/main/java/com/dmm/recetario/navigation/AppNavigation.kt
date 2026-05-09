package com.dmm.recetario.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dmm.recetario.core.utils.extension.back
import com.dmm.recetario.core.utils.extension.backTo
import com.dmm.recetario.core.utils.extension.navigateTo
import com.dmm.recetario.ui.auth.login.LoginScreen
import com.dmm.recetario.ui.auth.register.RegisterScreen
import com.dmm.recetario.ui.home.HomeScreen

@Composable
fun AppNavigation (
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    NavDisplay (
        backStack = backStack,
        modifier = modifier,
        onBack = backStack::back,
        entryProvider = entryProvider {
            entry<Routes.Login> {
                LoginScreen (
                    onNavigateToHome = {
                        backStack.clear()
                        backStack.navigateTo(Routes.Home)
                    },
                    onNavigateToRegister = {
                        backStack.clear()
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
                        Log.d("LOGOUT", "Cerrando sesión")
                        Log.d("LOGOUT", "Backstack antes del clear: ${backStack.map { it }}")
                        backStack.clear()
                        Log.d("LOGOUT", "Backstack después del clear: ${backStack.map { it }}")
                        backStack.navigateTo(Routes.Login)
                        Log.d("LOGOUT", "Backstack después del navigate: ${backStack.map { it }}")
                     },
                    onCompleteForm = {
                        backStack.backTo(Routes.Home)
                    }
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

//@Composable
//fun AppNavigation (
//    navController: NavHostController,
//    startDestination: Any
//) {
//    NavHost (
//        navController = navController,
//        startDestination = startDestination
//    ) {
//        composable<Login> {
//            LoginScreen (
//                onNavigateToHome = {
//                    navController.navigate(Home) {
//                        popUpTo(Login) { inclusive = true }
//                    }
//                },
//                onNavigateToRegister = {
//                    navController.navigate(Register) {
//                        popUpTo(Login) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable<Register> {
//            RegisterScreen (
//                onNavigateToHome = {
//                    navController.navigate(Home) {
//                        popUpTo(Login) { inclusive = true }
//                    }
//                },
//                onNavigateToLogin = {
//                    navController.navigate(Login) {
//                        popUpTo(Register) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable<Home> {
//            HomeScreen(
//                onCategoryClick = {},
//                onSettingsClick = {},
//                onLogOutClick = {
//                    navController.navigate(Login) {
//                        popUpTo(Home) { inclusive = true }
//                    }
//                },
//                onCompleteForm = {}
//            )
//        }
//    }
//}