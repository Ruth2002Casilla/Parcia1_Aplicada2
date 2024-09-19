package edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {

    @Upsert()
    suspend fun save(algo: AlgoEntity)

    @Query(
        """
        SELECT * 
        FROM Algos 
        WHERE algoId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): AlgoEntity?

    @Delete
    suspend fun delete(da: AlgoEntity)

    @Query("SELECT * FROM Algos")
    fun getAll(): Flow<List<AlgoEntity>>
}

