package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.dao.VentasDao
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.ventasEntity
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.component.NavigationDrawer
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas.DeleteVentaScreen
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas.EditarVentasScreen
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas.ListVentasScreen
import edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas.VentaScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun NavigationNavHost(
    navHostController: NavHostController,
    VentaLista: Flow<List<ventasEntity>>,
    ventasDao: VentasDao
){
    val isDrawerVisible = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Screen.ventaListScreen) {

            //Metas
            composable<Screen.ventaListScreen> {
                ListVentasScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToVenta = {
                        navHostController.navigate(Screen.ventaListScreen)
                    },
                    createVenta = {
                        navHostController.navigate(Screen.createVentaScreen)
                    },
                    editVenta = {
                        navHostController.navigate(Screen.editarVentaScreen(it))
                    },
                    deleteVenta = {
                        navHostController.navigate(Screen.eliminarVentaScreen(it))
                    }
                )
            }

            composable<Screen.createVentaScreen> {
                VentaScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToVenta = {
                        navHostController.navigate(Screen.ventaListScreen)
                    }
                )
            }
            composable<Screen.editarVentaScreen> { backStackEntry ->
                val ventaId = backStackEntry.arguments?.getInt("ventaId")
                if (ventaId != null) {
                    EditarVentasScreen(
                        ventaId = ventaId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToVenta = {
                            navHostController.navigate(Screen.ventaListScreen) {
                                popUpTo(Screen.ventaListScreen) { inclusive = true }
                            }
                        }
                    )
                }
            }


            composable<Screen.eliminarVentaScreen> { backStackEntry ->
                val ventaId = backStackEntry.arguments?.getInt("ventaId")
                if (ventaId != null) {
                    DeleteVentaScreen(
                        ventaId = ventaId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToVenta = {
                            navHostController.navigate(Screen.ventaListScreen) {
                                popUpTo(Screen.ventaListScreen) { inclusive = true }
                            }
                        }
                    )
                }
            }

        }

        NavigationDrawer(
            isVisible = isDrawerVisible.value,
            onItemClick = { itemTitle ->
                when (itemTitle) {
                    "Home" -> navHostController.navigate(Screen.ventaListScreen)
                }
                isDrawerVisible.value = false
            },
            onClose = {
                isDrawerVisible.value = false
            }
        )

    }
}
