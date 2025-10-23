package com.example.patitapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.patitapp.data.AppState
import com.example.patitapp.view.HomeScreen
import com.example.patitapp.view.LoginScreen
import com.example.patitapp.view.ServiceListScreen
import com.example.patitapp.view.SigninScreen
import com.example.patitapp.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    appState: AppState
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // ---- Pantalla de Login ----
        composable("login") {
            val viewModel: UsuarioViewModel = viewModel()
            LoginScreen(navController, viewModel, appState)
        }

        // ---- Pantalla de Registro ----
        composable("signin") {
            val viewModel: UsuarioViewModel = viewModel()
            SigninScreen(navController, viewModel, appState)
        }

        // ---- Pantalla de Home ----
        composable("home") {
            HomeScreen(nav = navController)
        }

        // ---- Pantalla de Lista de servicios ----
        composable(
            route = "services?category={category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            ServiceListScreen(
                entry = backStackEntry,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
