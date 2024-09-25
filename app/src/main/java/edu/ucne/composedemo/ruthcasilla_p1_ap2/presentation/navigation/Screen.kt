package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    object ventaListScreen : Screen()

    @Serializable
    object createVentaScreen : Screen()

    @Serializable
    data class editarVentaScreen(val ventaId: Int) : Screen()

    @Serializable
    data class eliminarVentaScreen(val ventaId: Int) : Screen()

}