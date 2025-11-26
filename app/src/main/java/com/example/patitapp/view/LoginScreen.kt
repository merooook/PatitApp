package com.example.patitapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.patitapp.data.DataStoreManager
import com.example.patitapp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val loginResult by viewModel.loginResult.collectAsState() // Observamos el resultado

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = DataStoreManager(context)

    // --- MAGIA AQUÍ: Reaccionamos al cambio de estado ---
    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            // Guardar en DataStore
            scope.launch {
                dataStore.saveUser(estado.correo)
            }
            // Navegar
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            // Resetear para evitar loops
            viewModel.resetLoginState()
        } else if (loginResult == false) {
            Toast.makeText(context, "Error al ingresar. Verifique datos.", Toast.LENGTH_SHORT).show()
            viewModel.resetLoginState() // Reseteamos para permitir intentar de nuevo
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Campo Correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            label = { Text("Correo") },
            isError = estado.errores.correo != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (estado.errores.correo != null) {
            Text(text = estado.errores.correo!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Password
        OutlinedTextField(
            value = estado.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.password != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (estado.errores.password != null) {
            Text(text = estado.errores.password!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Login
        Button(
            onClick = {
                // Solo llamamos a la función, no esperamos retorno aquí
                viewModel.autenticarUsuario()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = estado.puedeIngresar
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("signin") }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}