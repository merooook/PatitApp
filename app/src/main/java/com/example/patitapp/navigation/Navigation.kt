package com.example.patitapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.patitapp.data.AppState
import com.example.patitapp.view.LoginScreen
import com.example.patitapp.view.SigninScreen
import com.example.patitapp.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    appState: AppState
) {
    NavHost(
        navController = navController, startDestination = "login",
    ) {
        composable("login") {
            val viewModel: UsuarioViewModel = viewModel()
            LoginScreen(navController, viewModel, appState)
        }
        composable("signin") {
            val viewModel: UsuarioViewModel = viewModel()
            SigninScreen(navController, viewModel, appState)
        }
    }
}