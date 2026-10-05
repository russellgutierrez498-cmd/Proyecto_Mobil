package com.example.proyecto_mobil.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_mobil.ui.components.CasillaComposable
import com.example.proyecto_mobil.ui.components.FinDeJuegoDialog
import com.example.proyecto_mobil.viewmodel.BuscaminasViewModel

@Composable
fun BuscaminasScreen(viewModel: BuscaminasViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Buscaminas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Toca para destapar | Mantén para 🚩",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Puntuación: ${uiState.puntuacion}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                uiState.board.forEach { fila ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        fila.forEach { celda ->
                            CasillaComposable(
                                cell = celda,
                                onClick = { viewModel.revelarCasilla(celda.row, celda.col) },
                                onLongClick = { viewModel.colocarBandera(celda.row, celda.col) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        FinDeJuegoDialog(
            isVisible = uiState.isGameOver || uiState.isGameWon,
            isGameWon = uiState.isGameWon
        )

        Button(
            onClick = { viewModel.iniciarJuego() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Reiniciar Juego", fontSize = 16.sp)
        }
    }
}