package edu.ucne.composedemo.ruthcasilla_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.RuthCasilla_P1_Ap2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuthCasilla_P1_Ap2Theme {

                }
            }
        }
    }


