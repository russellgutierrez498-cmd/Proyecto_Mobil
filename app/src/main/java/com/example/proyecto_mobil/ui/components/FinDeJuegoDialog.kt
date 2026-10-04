package com.example.proyecto_mobil.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinDeJuegoDialog(
    isWon: Boolean,
    onRestart: () -> Unit
) {
    val titulo = if (isWon) "VICTORIA" else "BOOM"
    val mensaje = if (isWon) {
        " Lograste despejar todo el tablero sin detonar ninguna mina."
    } else {
        "Has pisado una mina. Todo el campo ha explotado."
    }
    val colorTitulo = if (isWon) Color(0xFF2E7D32) else Color(0xFFD32F2F)

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorTitulo,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mensaje,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isWon) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reiniciar Juego", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}