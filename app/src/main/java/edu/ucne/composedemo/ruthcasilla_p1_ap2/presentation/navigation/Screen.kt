package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    object ListScreen : Screen()

    @Serializable
    data class RegistroScreen(val Id: Int?) : Screen()

}