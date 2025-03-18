package com.example.chess.figures

import androidx.compose.ui.graphics.Color

class Rook(x: Int, y: Int, color: Color) : Figure(x, y, color)  {
    override val name = "Rook"
    override val icon = "♜"

    override fun checkIfLegalMove(newX: Int, newY: Int, figures: List<Figure>): Boolean {
        return (x == newX || y == newY) && !isRookBlocked(newX, newY, figures)
    }

    private fun isRookBlocked(x: Int, y: Int, figures: List<Figure>): Boolean {
        if (this.x == x) {
            val minY = minOf(this.y, y)
            val maxY = maxOf(this.y, y)
            return figures.any { it.x == x && it.y in minY+1 until maxY }
        } else if (this.y == y) {
            val minX = minOf(this.x, x)
            val maxX = maxOf(this.x, x)
            return figures.any { it.y == y && it.x in minX+1 until maxX }
        }
        return false
    }
}