package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.patitapp.data.AppState
import com.example.patitapp.viewmodel.UsuarioViewModel
import com.example.patitapp.data.DataStoreManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
    appState: AppState
) {
    val estado by viewModel.estado.collectAsState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
            // Campo de correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Botón de login
            Button(
                onClick = {
                    if (viewModel.autenticarUsuario()) {
                        val dataStoreManager = DataStoreManager(navController.context)
                        val correoIngresado = viewModel.estado.value.correo
                        scope.launch { dataStoreManager.saveUser(correoIngresado) }

                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                enabled = estado.puedeIngresar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate("signin") }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}
