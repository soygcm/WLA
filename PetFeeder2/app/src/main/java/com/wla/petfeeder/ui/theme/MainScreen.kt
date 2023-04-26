package com.wla.petfeeder.ui.theme

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
// @SuppressLint("ServiceCast")
@Composable
fun ProximitySensorExample() {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    val handler = Handler(Looper.getMainLooper())

    var proximityTriggered by remember { mutableStateOf(false) }

    val proximityListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
                proximityTriggered = true
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(proximityListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(proximityListener)
        }
    }

    if (proximityTriggered) {
        // Aquí llamarías a tu función de captura de fotos
        handler.postDelayed({ proximityTriggered = false }, 10000L)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text(text = "Proximity Sensor Example") })
        },
        content = {
            Box(
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bring your hand close to the sensor to trigger",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    onDraw = {
                        if (proximityTriggered) {
                            drawCircle(Color.Red, radius = 50f)
                        }
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProximitySensorExamplePreview() {
    ProximitySensorExample()
}
