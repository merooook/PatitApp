package com.example.patitapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patitapp.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninScreen(
    navController: NavHostController,
    viewModel: UsuarioViewModel
    // CORRECCIÓN: Quitamos appState porque ya no se usa
) {
    val estado by viewModel.estado.collectAsState()
    val loginResult by viewModel.loginResult.collectAsState() // Observamos el resultado del registro
    val context = LocalContext.current

    // --- LÓGICA DE NAVEGACIÓN ---
    // Si loginResult cambia a true, significa que el registro fue exitoso
    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
            // Navegamos al Home (Auto-login)
            navController.navigate("home") {
                popUpTo("signin") { inclusive = true }
                popUpTo("login") { inclusive = true }
            }
            viewModel.resetLoginState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Crear Cuenta") } // Cambié "Sign In" por "Crear Cuenta" para no confundir
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
            // USUARIO
            OutlinedTextField(
                value = estado.usuario,
                onValueChange = viewModel::onUsuarioChange,
                label = { Text("Usuario") },
                isError = estado.errores.usuario != null,
                supportingText = {
                    estado.errores.usuario?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // CORREO
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // PASSWORD
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(), // Ocultar caracteres
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // CONFIRM PASSWORD
            OutlinedTextField(
                value = estado.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text("Confirmar Contraseña") },
                visualTransformation = PasswordVisualTransformation(), // Ocultar caracteres
                isError = estado.errores.confirmPassword != null,
                supportingText = {
                    estado.errores.confirmPassword?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // DIRECCION
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // TERMINOS
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }
            Spacer(Modifier.height(16.dp))

            // BOTON
            Button(
                onClick = {
                    // Solo ejecutamos la acción, la navegación ocurre arriba en LaunchedEffect
                    viewModel.registrarUsuario()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = estado.aceptaTerminos // Deshabilitar si no acepta términos
            ) {
                Text("Registrarse")
            }
        }
    }
}