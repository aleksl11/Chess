package com.example.chess.figures

import android.util.Log
import androidx.compose.ui.graphics.Color

class Pawn(x: Int, y: Int, color: Color) : Figure(x, y, color) {
    override val name = "Pawn"
    override val icon = "♟"
    var isFirstMove = true

    override fun checkIfLegalMove(newX: Int, newY: Int, figures: List<Figure>): Boolean {
        var isLegal = true
        isLegal =  if (this.color == Color.LightGray) { //move white
            when {
                x != newX -> false
                isFirstMove && (newY == y + 2 || newY == y + 1) -> true
                !isFirstMove && newY == y + 1 -> true
                else -> false
            }
        } else { //move black
            when {
                x != newX -> false
                isFirstMove && (newY == y - 2 || newY == y - 1) -> true
                !isFirstMove && newY == y - 1 -> true
                else -> false
            }
        }
        isLegal = isLegal && !isSpaceOccupied(newX, newY, figures)
        //enable pawn to take other pieces
        if (isSpaceOccupiedByEnemy(newX, newY, figures)){
            Log.i("Pawn", "Taking piece")
            isLegal = if (this.color == Color.LightGray) {
                (newX == x + 1 || newX == x - 1) && newY == y + 1
            } else {
                (newX == x + 1 || newX == x - 1) && newY == y - 1
            }
        }


        return isLegal
    }

    fun setFirstMove() {
        isFirstMove = false
    }

    private fun isSpaceOccupied(x: Int, y: Int, figures: List<Figure>): Boolean {
        return figures.any { it.x == x && it.y == y }
    }

    private fun isSpaceOccupiedByEnemy(x: Int, y: Int, figures: List<Figure>): Boolean {
        return figures.any { it.x == x && it.y == y && it.color != this.color }
    }
}
