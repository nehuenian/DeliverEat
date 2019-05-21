package com.isw.delivereat.db.dao

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isw.delivereat.dto.Municipio
import com.isw.delivereat.dto.MunicipioSearchResult
import java.util.*

/**
 * Interface for Municipio database access
 *
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Dao
abstract class MunicipioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(municipios: List<Municipio>)

    @Query("SELECT * FROM Municipios")
    abstract fun getAllMunicipios(): LiveData<List<Municipio>>

    @Query("SELECT * FROM Municipios WHERE provincia_id = :provinciaId")
    abstract fun getMunicipiosForProvincia(provinciaId: Int): LiveData<List<Municipio>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: MunicipioSearchResult)

    @Query("SELECT * FROM MunicipioSearchResult WHERE `nombre` = :nombre")
    abstract fun search(nombre: String): LiveData<MunicipioSearchResult>

    fun loadOrdered(municipioIds: List<Int>): LiveData<List<Municipio>> {
        val order = SparseIntArray()
        municipioIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return Transformations.map(loadById(municipioIds)) { municipios ->
            Collections.sort(municipios) { m1, m2 ->
                val pos1 = order.get(m1.id)
                val pos2 = order.get(m2.id)
                pos1 - pos2
            }
            municipios
        }
    }

    @Query("SELECT * FROM Municipios WHERE id in (:municipioIds)")
    protected abstract fun loadById(municipioIds: List<Int>): LiveData<List<Municipio>>

    @Query("SELECT * FROM MunicipioSearchResult WHERE `nombre` = :nombre")
    abstract fun findSearchResult(nombre: String): MunicipioSearchResult?
}