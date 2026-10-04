package com.example.proyecto_mobil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_mobil.model.CellState

@Composable
fun CasillaComposable(
    cell: CellState,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        cell.isRevealed && cell.isMine -> Color(0xFFFFCDD2)
        cell.isRevealed -> Color(0xFFE0E0E0)
        else -> Color(0xFFBDBDBD)
    }

    val textColor = when (cell.minesAround) {
        1 -> Color(0xFF1976D2) // Azul
        2 -> Color(0xFF388E3C) // Verde
        3 -> Color(0xFFD32F2F) // Rojo
        4 -> Color(0xFF7B1FA2) // Púrpura
        else -> Color(0xFFE65100) // Naranja
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(backgroundColor)
            .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (cell.isRevealed) {
            if (cell.isMine) {
                Text("💣", fontSize = 16.sp)
            } else if (cell.minesAround > 0) {
                Text(
                    text = cell.minesAround.toString(),
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        } else if (cell.isFlagged) {
            Text("🚩", fontSize = 16.sp)
        }
    }
}