package edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.ventasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentasDao {
    @Upsert
    suspend fun save(venta: ventasEntity)

    @Query("SELECT * FROM ventas WHERE nombreCliente = :nombreClientes LIMIT 1")
    suspend fun findByNombre(nombreClientes: String): ventasEntity?

    @Query(
        """
        SELECT * 
        FROM Ventas 
        WHERE ventaId = :id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): ventasEntity?

    @Delete
    suspend fun delete(venta: ventasEntity)

    @Query("SELECT * FROM Ventas")
    fun getAll(): Flow<List<ventasEntity>>

}

