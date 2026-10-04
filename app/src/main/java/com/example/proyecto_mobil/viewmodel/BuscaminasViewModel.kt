package com.example.proyecto_mobil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyecto_mobil.model.BuscaminasUiState
import com.example.proyecto_mobil.model.CellState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BuscaminasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BuscaminasUiState())
    val uiState: StateFlow<BuscaminasUiState> = _uiState.asStateFlow()

    init {
        iniciarJuego()
    }

    fun iniciarJuego() {
        val filas = 8
        val columnas = 8

        // Crea una matriz 8x8 vacía para empezar
        val tableroVacio = List(filas) { f ->
            List(columnas) { c ->
                CellState(row = f, col = c)
            }
        }

        // Aquí agregarías la lógica para colocar minas aleatorias (Random)

        _uiState.value = BuscaminasUiState(
            board = tableroVacio,
            isGameOver = false
        )
    }

    fun revelarCasilla(row: Int, col: Int) {
        if (_uiState.value.isGameOver) return

        val tableroActual = _uiState.value.board.map { it.toMutableList() }.toMutableList()
        val casilla = tableroActual[row][col]

        if (casilla.isRevealed || casilla.isFlagged) return

        if (casilla.isMine) {
            // Lógica de perder juego
            tableroActual[row][col] = casilla.copy(isRevealed = true)
            _uiState.update { it.copy(board = tableroActual, isGameOver = true) }
        } else {
            // Lógica normal al destapar
            tableroActual[row][col] = casilla.copy(isRevealed = true)
            _uiState.update { it.copy(board = tableroActual) }
        }
    }

    fun colocarBandera(row: Int, col: Int) {
        if (_uiState.value.isGameOver) return

        val tableroActual = _uiState.value.board.map { it.toMutableList() }.toMutableList()
        val casilla = tableroActual[row][col]

        if (!casilla.isRevealed) {
            tableroActual[row][col] = casilla.copy(isFlagged = !casilla.isFlagged)
            _uiState.update { it.copy(board = tableroActual) }
        }
    }
}