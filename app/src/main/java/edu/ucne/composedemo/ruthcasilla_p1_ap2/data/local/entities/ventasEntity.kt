package edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class ventasEntity (
    @PrimaryKey(autoGenerate = true)
    val ventaId: Int? = null,
    val nombreCliente: String?,
    val galones: Double?,
    val descuentoGalon: Double?,
    val precio: Double?,
    val totalDescontado: Double?,
    val total: Double?
)
