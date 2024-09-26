package edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.dao.VentasDao
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.ventasEntity

@Database(
    entities = [
        ventasEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VentaDb : RoomDatabase() {
    abstract fun VentasDao(): VentasDao
}
