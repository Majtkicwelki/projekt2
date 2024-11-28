package com.example.aplikacja2

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var czujnik: Sensor? = null

    private var swiatlosc by mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        czujnik = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        setContent {
            LuxometerScreen(swiatlosc)
        }
    }

    override fun onResume() {
        super.onResume()
        czujnik?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            swiatlosc = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }
}

@Composable
fun LuxometerScreen(swiatlosc: Float) {
    MaterialTheme {
        Surface {
            Text(
                text = "Natężenie światła: $swiatlosc lux",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLuxometerScreen() {
    LuxometerScreen(swiatlosc = 0f)
}