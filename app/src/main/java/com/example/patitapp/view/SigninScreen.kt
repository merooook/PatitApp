package com.example.patitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.patitapp.data.AppState
import com.example.patitapp.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninScreen(
    navController: NavHostController,
    viewModel: UsuarioViewModel,
    appState: AppState
){
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor= MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title={Text("Sign In")
                }
            )
        }
    ){padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = {Text("Correo")},
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let{
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                label = {Text("Password")},
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let{
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = estado.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = {Text("Confirmar Contraseña")},
                isError = estado.errores.confirmPassword != null,
                supportingText = {
                    estado.errores.confirmPassword?.let{
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = {Text("Dirección")},
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let{
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }
            Spacer(Modifier.height(16.dp))
        }
        Button(
            onClick = {
                viewModel.validarDatos()
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Sign In")
        }
        //navegación a Home
    }
}