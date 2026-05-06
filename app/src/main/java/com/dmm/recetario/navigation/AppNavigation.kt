package com.dmm.recetario.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dmm.recetario.ui.auth.login.LoginScreen
import com.dmm.recetario.ui.auth.register.RegisterScreen

@Composable
fun AppNavigation (
    navController: NavHostController,
    startDestination: Any
) {
    NavHost (
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Login> {
            LoginScreen (
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Register) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Register> {
            RegisterScreen (
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo(Register) { inclusive = true }
                    }
                }
            )
        }

        composable<Home> {

        }
    }
}