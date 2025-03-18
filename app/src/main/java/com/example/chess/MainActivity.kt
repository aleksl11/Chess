package com.example.chess

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chess.figures.Bishop
import com.example.chess.figures.Figure
import com.example.chess.figures.Knight
import com.example.chess.figures.Pawn
import com.example.chess.figures.Rook
import com.example.chess.ui.theme.ChessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainBoard()
                }
            }
        }
    }
}

@Composable
fun MainBoard() {
    var colorToMove = remember { mutableStateOf(Color.LightGray) }
    val figures = remember { mutableStateListOf<Figure>(
        // White figures
        Rook(0, 0, Color.LightGray),
        Rook(7, 0, Color.LightGray),
        Knight(1, 0, Color.LightGray),
        Knight(6, 0, Color.LightGray),
        Bishop(2, 0, Color.LightGray),
        Bishop(5, 0, Color.LightGray),
        // White pawns
        Pawn(0, 1, Color.LightGray),
        Pawn(1, 1, Color.LightGray),
        Pawn(2, 1, Color.LightGray),
        Pawn(3, 1, Color.LightGray),
        Pawn(4, 1, Color.LightGray),
        Pawn(5, 1, Color.LightGray),
        Pawn(6, 1, Color.LightGray),
        Pawn(7, 1, Color.LightGray),

        //Black figures
        Rook(0, 7, Color.DarkGray),
        Rook(7, 7, Color.DarkGray),
        Knight(6, 7, Color.DarkGray),
        Knight(1, 7, Color.DarkGray),
        Bishop(2, 7, Color.DarkGray),
        Bishop(5, 7, Color.DarkGray),
        // Black pawns
        Pawn(0, 6, Color.DarkGray),
        Pawn(1, 6, Color.DarkGray),
        Pawn(2, 6, Color.DarkGray),
        Pawn(3, 6, Color.DarkGray),
        Pawn(4, 6, Color.DarkGray),
        Pawn(5, 6, Color.DarkGray),
        Pawn(6, 6, Color.DarkGray),
        Pawn(7, 6, Color.DarkGray),
    ) }
    var isMoving by remember { mutableStateOf(false) }
    var selectedX by remember { mutableStateOf(-1) }
    var selectedY by remember { mutableStateOf(-1) }

    var chosenSquare by remember { mutableStateOf(Pair<Int, Int>(-1,-1)) }

    Column (modifier = Modifier.fillMaxSize()) {
        for(i in 0..7){
            Row {
                for(j in 0..7){
                    val color = if((i+j) % 2 == 0) Color.White else Color.Black
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .background(if(chosenSquare == Pair<Int, Int>(j,i)) Color.Cyan else color)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                Log.i("Clicked", "Clicked: $j $i")
                                if (!isMoving) {
                                    // First click: select a figure if present.
                                    figures.firstOrNull { it.x == j && it.y == i && it.color == colorToMove.value}?.let {
                                        selectedX = j
                                        selectedY = i
                                        isMoving = true
                                        chosenSquare = Pair(j, i)
                                        Log.i("Selected", "Selected: $selectedX $selectedY")
                                    }
                                } else {
                                    if (selectedX == j && selectedY == i) { // if the selected square is clicked again, cancel the move.
                                        isMoving = false
                                        chosenSquare = Pair(-1, -1)
                                    }else { // Second click: attempt to move the selected figure.
                                        figures.firstOrNull { it.x == selectedX && it.y == selectedY }
                                            ?.let { figure ->
                                                if (figure.checkIfLegalMove(j, i, figures) && !isSpaceOccupiedByTeam(j, i, figures, colorToMove.value)) {
                                                    figure.move(j, i)
                                                    if (figure.name == "Pawn" && (figure as Pawn).isFirstMove) {
                                                        figure.setFirstMove()
                                                    }
                                                    isMoving = false
                                                    chosenSquare = Pair(-1, -1)
                                                    if (isSpaceOccupiedByOpponent(
                                                            j,
                                                            i,
                                                            figures,
                                                            colorToMove.value
                                                        )
                                                    ) {
                                                        figures.remove(figures.first { it.x == j && it.y == i && it.color != colorToMove.value })
                                                    }
                                                    colorToMove.value =
                                                        changeColor(colorToMove.value)
                                                }

                                            }
                                    }
                                }
                            }
                    ){
                        figures.forEach {
                            if(it.x == j && it.y == i){
                                Text(
                                    text = it.icon,
                                    color = it.color,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    fontSize = 40.sp,

                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

fun changeColor(color: Color): Color {
    return if(color == Color.LightGray) Color.DarkGray else Color.LightGray
}

fun isSpaceOccupiedByTeam(x: Int, y: Int, figures: List<Figure>, color: Color): Boolean {
    return figures.any { it.x == x && it.y == y && it.color == color }
}

fun isSpaceOccupiedByOpponent(x: Int, y: Int, figures: List<Figure>, color: Color): Boolean {
    return figures.any { it.x == x && it.y == y && it.color != color }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChessTheme {
        MainBoard()
    }
}