package com.example.proyecto_mobil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyecto_mobil.model.BuscaminasUiState
import com.example.proyecto_mobil.model.CellState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class BuscaminasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BuscaminasUiState())
    val uiState: StateFlow<BuscaminasUiState> = _uiState.asStateFlow()

    private val filas = 8
    private val columnas = 8
    private val totalMinas = 10

    init {
        iniciarJuego()
    }

    fun iniciarJuego() {
        // 1. Crear matriz vacía
        val matriz = Array(filas) { f ->
            Array(columnas) { c ->
                CellState(row = f, col = c)
            }
        }

        var minasColocadas = 0
        while (minasColocadas < totalMinas) {
            val f = Random.nextInt(filas)
            val c = Random.nextInt(columnas)
            if (!matriz[f][c].isMine) {
                matriz[f][c] = matriz[f][c].copy(isMine = true)
                minasColocadas++
            }
        }

        for (f in 0 until filas) {
            for (c in 0 until columnas) {
                if (!matriz[f][c].isMine) {
                    var contador = 0
                    for (df in -1..1) {
                        for (dc in -1..1) {
                            val nf = f + df
                            val nc = c + dc
                            if (nf in 0 until filas && nc in 0 until columnas && matriz[nf][nc].isMine) {
                                contador++
                            }
                        }
                    }
                    matriz[f][c] = matriz[f][c].copy(minesAround = contador)
                }
            }
        }

        val tablero = matriz.map { it.toList() }.toList()

        _uiState.value = BuscaminasUiState(
            board = tablero,
            isGameOver = false,
            isGameWon = false,
            rows = filas,
            cols = columnas
        )
    }

    fun revelarCasilla(row: Int, col: Int) {
        val estadoActual = _uiState.value
        if (estadoActual.isGameOver || estadoActual.isGameWon) return

        val casilla = estadoActual.board[row][col]
        if (casilla.isRevealed || casilla.isFlagged) return

        val nuevoTablero = estadoActual.board.map { it.toMutableList() }.toMutableList()

        if (casilla.isMine) {
            for (f in 0 until filas) {
                for (c in 0 until columnas) {
                    if (nuevoTablero[f][c].isMine) {
                        nuevoTablero[f][c] = nuevoTablero[f][c].copy(isRevealed = true)
                    }
                }
            }
            _uiState.update { it.copy(board = nuevoTablero, isGameOver = true) }
        } else {
            revelarRecursivo(nuevoTablero, row, col)

            val gano = verificarVictoria(nuevoTablero)

            _uiState.update {
                it.copy(
                    board = nuevoTablero,
                    isGameWon = gano
                )
            }
        }
    }

    private fun revelarRecursivo(tablero: MutableList<MutableList<CellState>>, f: Int, c: Int) {
        if (f !in 0 until filas || c !in 0 until columnas) return
        val celda = tablero[f][c]
        if (celda.isRevealed || celda.isFlagged || celda.isMine) return

        tablero[f][c] = celda.copy(isRevealed = true)

        if (celda.minesAround == 0) {
            for (df in -1..1) {
                for (dc in -1..1) {
                    if (df != 0 || dc != 0) {
                        revelarRecursivo(tablero, f + df, c + dc)
                    }
                }
            }
        }
    }

    fun colocarBandera(row: Int, col: Int) {
        val estadoActual = _uiState.value
        if (estadoActual.isGameOver || estadoActual.isGameWon) return

        val casilla = estadoActual.board[row][col]
        if (casilla.isRevealed) return

        val nuevoTablero = estadoActual.board.map { it.toMutableList() }.toMutableList()
        nuevoTablero[row][col] = casilla.copy(isFlagged = !casilla.isFlagged)

        _uiState.update { it.copy(board = nuevoTablero) }
    }

    private fun verificarVictoria(tablero: List<List<CellState>>): Boolean {
        for (f in 0 until filas) {
            for (c in 0 until columnas) {
                val celda = tablero[f][c]
                if (!celda.isMine && !celda.isRevealed) {
                    return false
                }
            }
        }
        return true
    }
}