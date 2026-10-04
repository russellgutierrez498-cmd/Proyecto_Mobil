package com.example.proyecto_mobil.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_mobil.ui.components.CasillaComposable
import com.example.proyecto_mobil.viewmodel.BuscaminasViewModel

@Composable
fun BuscaminasScreen(viewModel: BuscaminasViewModel) {
    // Observamos el estado del flujo que viene del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isGameOver) {
            Text("¡Boom! Juego Terminado", color = androidx.compose.ui.graphics.Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Generar Tablero
        Column {
            uiState.board.forEach { fila ->
                Row {
                    fila.forEach { celda ->
                        CasillaComposable(
                            cell = celda,
                            onClick = { viewModel.revelarCasilla(celda.row, celda.col) },
                            onLongClick = { viewModel.colocarBandera(celda.row, celda.col) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.iniciarJuego() }) {
            Text("Reiniciar Juego")
        }
    }
}