package edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.repository

import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.dao.VentasDao
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.VentasEntity
import javax.inject.Inject

class VentaRepository @Inject constructor(
    private val ventasDao: VentasDao
){
    suspend fun save(venta: VentasEntity) = ventasDao.save(venta)

    suspend fun delete(venta: VentasEntity) = ventasDao.delete(venta)

    suspend fun getVenta(id: Int) = ventasDao.find(id)

    fun getAll() = ventasDao.getAll()
}

