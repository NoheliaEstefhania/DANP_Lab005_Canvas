package com.example.danp_lab005_canvas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_lab005_canvas2.ui.theme.DANP_Lab005_Canvas2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawRoom()
            DrawSquare()
        }
    }
}

@Composable
fun DrawRoom() {
    Canvas(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        drawRect(
            color = Color.Gray,
            size = this.size
        )

        // Dibuja el cuadrado rojo.
        drawRect(
            color = Color.Red,
            topLeft = Offset(100f, 100f),
            size = Size(100f, 100f),
            style = Stroke(width = 5.dp.toPx())
        )
    }
}

@Composable
fun DrawSquare() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareSize = 100.dp.toPx()
        val topLeft = Offset(100f, 100f)
        val topRight = Offset(topLeft.x + squareSize, topLeft.y)
        val bottomLeft = Offset(topLeft.x, topLeft.y + squareSize)
        val bottomRight = Offset(topLeft.x + squareSize, topLeft.y + squareSize)

        // Draw lines to form a square
        drawLine(Color.Black, topLeft, topRight)
        drawLine(Color.Black, topRight, bottomRight)
        drawLine(Color.Black, bottomRight, bottomLeft)
        drawLine(Color.Black, bottomLeft, topLeft)
    }
}