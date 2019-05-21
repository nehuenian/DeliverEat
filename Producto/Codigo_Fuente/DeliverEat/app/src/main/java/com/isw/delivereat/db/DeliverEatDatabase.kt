package com.isw.delivereat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isw.delivereat.db.dao.DireccionDao
import com.isw.delivereat.db.dao.MunicipioDao
import com.isw.delivereat.dto.Direccion
import com.isw.delivereat.dto.Municipio
import com.isw.delivereat.dto.MunicipioSearchResult

/**
 * Database description.
 *
 * Created by Jeremias Gersicich on 2019-05-20
 */
@Suppress("KDocMissingDocumentation")
@Database(
    entities = [Municipio::class, Direccion::class, MunicipioSearchResult::class],
    version = 1,
    exportSchema = false
)
abstract class DeliverEatDatabase : RoomDatabase() {

    abstract fun municipioDao(): MunicipioDao

    abstract fun direccionDao(): DireccionDao

    companion object {
        private var INSTANCE: DeliverEatDatabase? = null

        /**
         * Retrieves an Instance of [DeliverEatDatabase] as in Singleton pattern
         */
        fun getInstance(context: Context): DeliverEatDatabase? {
            if (INSTANCE == null) {
                synchronized(DeliverEatDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DeliverEatDatabase::class.java, "delivereat.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}