package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.AppState
import com.example.patitapp.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
    appState: AppState
) {
    val estado by viewModel.estado.collectAsState()

    // CAMBIO:
    // Dejo comentado este bloque porque hacia que hubiera navegación automática cada vez que el estado cambiaba y los campos quedaban “válidos”

    // LaunchedEffect(estado) {
    //     if (estado.errores.usuario == null && estado.errores.password == null
    //         && estado.usuario.isNotBlank() && estado.password.isNotBlank()
    //     ) {
    //         navController.navigate("home") {
    //             popUpTo("login") { inclusive = true }
    //         }
    //     }
    // }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Login") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = estado.usuario,
                onValueChange = viewModel::onUsuarioChange,
                label = { Text("Usuario") },
                isError = estado.errores.usuario != null,
                supportingText = {
                    estado.errores.usuario?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Password") },
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // CAMBIO: usar validarLogin() (solo usuario + password) y navegar si es válido

            Button(
                onClick = {
                    if (viewModel.validarLogin()) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }


            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("signin") }) {
                Text("¿No tienes cuenta?, Regístrate Aquí")
            }
        }
    }
}
