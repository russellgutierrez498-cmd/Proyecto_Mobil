package com.example.proyecto_mobil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.proyecto_mobil.model.CellState

@Composable
fun CasillaComposable(
    cell: CellState,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val bgColor = if (cell.isRevealed) Color.LightGray else Color.Gray

    Box(
        modifier = Modifier
            .size(45.dp)
            .background(bgColor)
            .border(1.dp, Color.DarkGray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (cell.isRevealed) {
            if (cell.isMine) Text("💣")
            else if (cell.minesAround > 0) Text(cell.minesAround.toString())
        } else if (cell.isFlagged) {
            Text("🚩")
        }
    }
}