package com.example.danp_lab005_canvas2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    private val orientationViewModel: OrientationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawRoomWithPolygon(orientationViewModel)
        }
    }
}

class OrientationViewModel : ViewModel(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometerValues = FloatArray(3)
    private var magneticFieldValues = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val _orientationValues = mutableStateOf(FloatArray(3))

    val orientationValues: State<FloatArray> = _orientationValues

    fun startListening(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, accelerometerValues, 0, 3)
            Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, magneticFieldValues, 0, 3)
        }

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticFieldValues)
        val newOrientationValues = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, newOrientationValues)
        _orientationValues.value = newOrientationValues
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        //para cambios en la precisión si es necesario
    }
}

@Composable
fun DrawRoomWithPolygon(orientationViewModel: OrientationViewModel) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        orientationViewModel.startListening(context)
    }

    var isRotationEnabled by remember { mutableStateOf(true) }
    val orientationValues by orientationViewModel.orientationValues

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            DrawRoom()
            RotatedPolygonCanvas(orientationValues, isRotationEnabled)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { isRotationEnabled = !isRotationEnabled },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(if (isRotationEnabled) "Fijar" else "Rotar")
        }
    }
}
@Composable
fun DrawRoom() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawRect(
            color = Color.LightGray,
            size = this.size
        )
    }
}

@Composable
fun RotatedPolygonCanvas(orientationValues: FloatArray, isRotationEnabled: Boolean) {
    val azimuth = Math.toDegrees(orientationValues[0].toDouble()).toFloat()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val polygonPath = Path().apply {
            moveTo(500f, 500f)
            lineTo(300f, 1000f)
            lineTo(700f, 1000f)
            close()
        }

        if (isRotationEnabled) {

            rotate(degrees = -azimuth, pivot = Offset(500f, 750f)) {
                drawPath(polygonPath, color = Color.Magenta)
            }
        } else {
            drawPath(polygonPath, color = Color.Magenta)
        }
    }
}