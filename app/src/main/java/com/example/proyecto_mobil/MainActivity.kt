package com.example.proyecto_mobil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_mobil.ui.BuscaminasScreen
import com.example.proyecto_mobil.ui.theme.Proyecto_MobilTheme // Revisa si tu tema no tiene guion bajo
import com.example.proyecto_mobil.viewmodel.BuscaminasViewModel
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto_MobilTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(paddingValues = innerPadding)) {

                        // SOLUCIÓN: Instanciar el ViewModel usando la función delegada de Compose
                        val viewModel: BuscaminasViewModel = viewModel()

                        BuscaminasScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}