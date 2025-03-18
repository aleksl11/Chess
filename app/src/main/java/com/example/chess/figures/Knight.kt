package com.example.chess.figures

import androidx.compose.ui.graphics.Color

class Knight(x: Int, y: Int, color: Color) : Figure(x, y, color) {
    override val name = "Knight"
    override val icon = "♞"

    override fun checkIfLegalMove(newX: Int, newY: Int, figures: List<Figure>): Boolean {
        return when {
            x + 2 == newX && y + 1 == newY -> true
            x + 2 == newX && y - 1 == newY -> true
            x - 2 == newX && y + 1 == newY -> true
            x - 2 == newX && y - 1 == newY -> true
            x + 1 == newX && y + 2 == newY -> true
            x + 1 == newX && y - 2 == newY -> true
            x - 1 == newX && y + 2 == newY -> true
            x - 1 == newX && y - 2 == newY -> true
            else -> false
        }
    }
}