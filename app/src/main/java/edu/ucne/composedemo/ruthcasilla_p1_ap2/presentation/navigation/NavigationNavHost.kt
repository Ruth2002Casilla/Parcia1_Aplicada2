package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.Purple80

@Composable
fun NavigationNavHost(
    navHostController: NavHostController)
{
    val isDrawerVisible = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Screen.ListScreen::class.java.simpleName) {

            composable<Screen.ListScreen> {
                Button(
                    onClick = {
                        navHostController.navigate(Screen.RegistroScreen::class.java.simpleName)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple80,
                    ),
                    shape = RoundedCornerShape(10.dp)
                    ) {

                    Text(text = "Registro")
                }
            }

            composable<Screen.RegistroScreen> {}

        }
        
    }
}

