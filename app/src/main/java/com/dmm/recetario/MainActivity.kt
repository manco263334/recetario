package com.dmm.recetario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.rememberNavBackStack
import com.dmm.recetario.navigation.AppNavigation
import com.dmm.recetario.ui.theme.RecetarioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val startDestination = viewModel.startDestination

            RecetarioTheme {
                if (startDestination != null) {
                    val backStack = rememberNavBackStack(startDestination)

                    AppNavigation (
                        backStack = backStack
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

//@AndroidEntryPoint
//class MainActivity: ComponentActivity() {
//    private val viewModel: MainViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        enableEdgeToEdge()
//
//        setContent {
//            val navController = rememberNavController()
//            val startDestination = viewModel.startDestination
//
//            RecetarioTheme {
//                if (startDestination != null) {
//                    AppNavigation (
//                        navController = navController,
//                        startDestination = startDestination
//                    )
//                } else {
//                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        CircularProgressIndicator()
//                    }
//                }
//            }
//        }
//    }
//}