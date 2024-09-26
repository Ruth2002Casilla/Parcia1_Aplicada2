package edu.ucne.composedemo.ruthcasilla_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.database.VentaDb
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.navigation.NavigationNavHost
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.RuthCasilla_P1_Ap2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var ventaDb: VentaDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ventaDb = Room.databaseBuilder(
            applicationContext,
            VentaDb::class.java,
            "Ventas.db"
        ).build()

        enableEdgeToEdge()

        val ventasDao = ventaDb.VentasDao()

        setContent {
            RuthCasilla_P1_Ap2Theme {
                val navController = rememberNavController()

                NavigationNavHost(
                    navHostController = navController,
                    VentaLista = ventasDao.getAll(),
                    ventasDao = ventasDao,
                )
            }
        }
    }
}
