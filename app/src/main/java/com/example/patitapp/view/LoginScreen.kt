package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.AppState
import com.example.patitapp.viewmodel.UsuarioViewModel
import com.example.patitapp.data.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
    appState: AppState
) {
    val estado by viewModel.estado.collectAsState()

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

            // CAMBIO: botón valida y guarda el usuario antes de navegar

            Button(
                onClick = {
                    if (viewModel.validarLogin()) {
                        // CAMBIO: se agrega guardado del usuario en DataStore
                        val dataStoreManager = DataStoreManager(navController.context)
                        val usuarioIngresado = viewModel.estado.value.usuario

                        GlobalScope.launch {
                            dataStoreManager.saveUser(usuarioIngresado)
                        }

                        // CAMBIO: navegación a Home después de guardar
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
