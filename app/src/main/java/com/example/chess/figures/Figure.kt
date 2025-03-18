package com.example.chess.figures

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

open class Figure(x: Int, y: Int, var color: Color) {
    open var x by mutableStateOf(x)
    open var y by mutableStateOf(y)
    open val icon: String = "♟"
    open val name = "Default"

    fun move(newX: Int, newY: Int) {
        x = newX
        y = newY
    }

    open fun checkIfLegalMove(newX: Int, newY: Int, figures: List<Figure>): Boolean {
        return true
    }
}
