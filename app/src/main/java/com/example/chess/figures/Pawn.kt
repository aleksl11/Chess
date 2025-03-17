package com.example.chess.figures

import androidx.compose.ui.graphics.Color

class Pawn(x: Int, y: Int, color: Color) : Figure(x, y, color) {
    override val name = "Pawn"
    override val icon = "♟"
    var isFirstMove = true

    override fun checkIfLegalMove(newX: Int, newY: Int): Boolean {
        return if (this.color == Color.LightGray) {
            when {
                x != newX -> false // Pawns cannot move sideways
                isFirstMove && (newY == y + 2 || newY == y + 1) -> true // Can move 1 or 2 squares forward on first move
                !isFirstMove && newY == y + 1 -> true // Normal move: 1 square forward
                else -> false
            }
        } else {
            when {
                x != newX -> false // Pawns cannot move sideways
                isFirstMove && (newY == y - 2 || newY == y - 1) -> true // Can move 1 or 2 squares forward on first move
                !isFirstMove && newY == y - 1 -> true // Normal move: 1 square forward
                else -> false
            }
        }
    }

    fun setFirstMove() {
        isFirstMove = false
    }
}
