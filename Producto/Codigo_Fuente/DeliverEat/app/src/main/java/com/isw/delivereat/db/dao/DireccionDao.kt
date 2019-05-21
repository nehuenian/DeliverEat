package com.isw.delivereat.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isw.delivereat.dto.Direccion

/**
 * Interface for Direccion database access
 *
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Dao
abstract class DireccionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(direcciones: List<Direccion>)

    @Query("SELECT * FROM Direcciones")
    abstract fun getAllDirecciones(): LiveData<List<Direccion>>
}