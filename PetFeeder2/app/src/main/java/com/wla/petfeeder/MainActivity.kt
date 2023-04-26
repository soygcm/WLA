package com.wla.petfeeder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wla.petfeeder.ui.theme.PetFeederTheme
import com.wla.petfeeder.ui.theme.ProximitySensorExample

class MainActivity : ComponentActivity() {
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
