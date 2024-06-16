package com.example.danp_lab005_canvas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawRoom() //dibuja el fondo
            DrawSquareWithRectLine() // dibuja un cuadrado con drawRect
            DrawSquareWithDrawLine() // dibuja un cuadrado con drawLine
            DrawSquareWithPath() //dibuja un cuadrado con Path
        }
    }
}

// Función composable para dibujar el fondo
@Composable
fun DrawRoom() {
    Canvas(
        modifier = Modifier
            .padding(20.dp) // Aplica un padding de 20.dp
            .fillMaxSize() // Ocupa todo el tamaño disponible
    ) {
        drawRect(
            color = Color.LightGray, // Color de fondo gris claro
            size = this.size // Tamaño del rectángulo igual al tamaño del canvas
        )
    }
}

// Función composable para dibujar un cuadrado rojo utilizando drawRect
@Composable
fun DrawSquareWithRectLine() {
    Canvas(
        modifier = Modifier.fillMaxSize() // Ocupa todo el tamaño disponible
    ) {
        drawRect(
            color = Color.Red, // Color rojo
            topLeft = Offset(60f, 60f), // Posición superior izquierda del cuadrado
            size = Size(100f, 100f), // Tamaño del cuadrado
            style = Stroke(width = 5.dp.toPx()) // Grosor del borde de 5.dp
        )
    }
}

// Función composable para dibujar un cuadrado rojo utilizando Path
@Composable
fun DrawSquareWithPath() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = androidx.compose.ui.graphics.Path().apply {
            val squareSize = 40.dp.toPx()
            val topLeft = Offset(60f, 440f) // Posición superior izquierda del cuadrado

            // Define el camino del cuadrado
            moveTo(topLeft.x, topLeft.y)
            lineTo(topLeft.x + squareSize, topLeft.y)
            lineTo(topLeft.x + squareSize, topLeft.y + squareSize)
            lineTo(topLeft.x, topLeft.y + squareSize)
            close() // Cierra el camino conectando el último punto con el primero
        }
        drawPath(path, Color.Red, style = Stroke(width = 5.dp.toPx())) // Dibuja el camino con color rojo y grosor de 5.dp
    }
}

// Función composable para dibujar un cuadrado rojo utilizando drawLine
@Composable
fun DrawSquareWithDrawLine() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareSize = 40.dp.toPx()
        val strokeWidth = 5.dp.toPx()
        val topLeft = Offset(60f, 830f) // Posición superior izquierda del cuadrado

        // Dibuja las líneas para formar un cuadrado rojo
        drawLine(
            color = Color.Red,
            start = topLeft,
            end = Offset(topLeft.x + squareSize, topLeft.y),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = Color.Red,
            start = Offset(topLeft.x + squareSize, topLeft.y),
            end = Offset(topLeft.x + squareSize, topLeft.y + squareSize),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = Color.Red,
            start = Offset(topLeft.x + squareSize, topLeft.y + squareSize),
            end = Offset(topLeft.x, topLeft.y + squareSize),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = Color.Red,
            start = Offset(topLeft.x, topLeft.y + squareSize),
            end = topLeft,
            strokeWidth = strokeWidth
        )
    }
}
