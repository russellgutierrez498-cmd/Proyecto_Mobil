package com.example.proyecto_mobil.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_mobil.ui.components.CasillaComposable
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
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Buscaminas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Toca: Destapar | Mantén: 🚩", fontSize = 12.sp, color = Color.Gray)
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
            ) {
                Text(
                    text = "Puntos: ${uiState.puntuacion}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.aspectRatio(1f)) {
                uiState.board.forEach { fila ->
                    Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
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

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = uiState.isGameOver || uiState.isGameWon,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            val colorFondo = if (uiState.isGameWon) Color(0xFFC8E6C9) else Color(0xFFFFCDD2)
            val colorTexto = if (uiState.isGameWon) Color(0xFF2E7D32) else Color(0xFFC62828)
            val mensaje = if (uiState.isGameWon) "¡VICTORIA! Despejaste todo." else "¡BOOM! Pisaste una mina."

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = colorFondo),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )
            }
        }

        Button(
            onClick = { viewModel.iniciarJuego() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Reiniciar Juego", fontSize = 16.sp)
        }
    }
}