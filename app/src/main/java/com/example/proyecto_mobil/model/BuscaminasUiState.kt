package com.example.proyecto_mobil.model

data class BuscaminasUiState(
    val board: List<List<CellState>> = emptyList(),
    val isGameOver: Boolean = false,
    val isGameWon: Boolean = false,
    val rows: Int = 8,
    val cols: Int = 8,
    val puntuacion: Int = 0

)