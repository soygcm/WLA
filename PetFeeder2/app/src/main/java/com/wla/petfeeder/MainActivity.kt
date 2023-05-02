package com.wla.petfeeder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.wla.petfeeder.ui.theme.PetFeederTheme
import com.wla.petfeeder.ui.theme.ProximitySensorExample

@Composable
fun CameraPermission(onPermissionGranted: () -> Unit) {
    var isGranted by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted = it; if (isGranted) onPermissionGranted() }
    Button(onClick = { requestPermissionLauncher.launch(android.Manifest.permission.CAMERA) }) {
        Text(text = "Request Camera Permission")
    }
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetFeederTheme {
                ProximitySensorExample()
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }
}

// @Composable
// fun Greeting(name: String) {
//    Text(text = "Hello $name!")
// }

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//    PetFeederTheme {
//        Greeting("Android")
//    }
// }
