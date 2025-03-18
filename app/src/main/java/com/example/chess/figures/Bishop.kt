package com.example.chess.figures

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

class Bishop(x: Int, y: Int, color: Color) : Figure(x, y, color) {
    override val name = "Bishop"
    override val icon = "♝"

    override fun checkIfLegalMove(newX: Int, newY: Int, figures: List<Figure>): Boolean {
        return (abs(x - newX) == abs(y - newY)) && !isBishopBlocked(newX, newY, figures)
    }

    private fun isBishopBlocked(x: Int, y: Int, figures: List<Figure>): Boolean {
        val xToMove = x - this.x
        val yToMove = y - this.y

        val xDirection = if (xToMove > 0) 1 else -1
        val yDirection = if (yToMove > 0) 1 else -1

        val spacesToCheck = List(abs(xToMove)-1) { i -> Pair(this.x + i+1 * xDirection, this.y + i+1 * yDirection) }
        return spacesToCheck.any { (x, y) -> figures.any { it.x == x && it.y == y } }
    }
}