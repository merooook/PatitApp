package com.example.patitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.patitapp.data.AppState
import com.example.patitapp.data.DataStoreManager
import com.example.patitapp.navigation.AppNavigation
import com.example.patitapp.ui.theme.PatitAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dataStore = DataStoreManager(applicationContext)
        val appState = AppState(dataStore)

        lifecycleScope.launch {
            appState.cargarDatos()
        }

        setContent {
             PatitAppTheme {
                MyApp(appState)
            }
        }
    }
}

@Composable
fun MyApp(appState: AppState){
    val navController = rememberNavController()

    MaterialTheme{
        AppNavigation(navController, appState)
    }
}