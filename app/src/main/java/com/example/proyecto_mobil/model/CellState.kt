package com.example.proyecto_mobil.model

data class CellState(
    val row: Int,
    val col: Int,
    val isMine: Boolean = false,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false,
    val minesAround: Int = 0
)