package com.shruti.pupwiki.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(PuppyBreedItem::class),
    version = 1
)

abstract class PupDatabase : RoomDatabase() {

    abstract fun pupDao(): PupDao

    //singleton
    companion object {
        @Volatile
        private var instance: PupDatabase? = null
        private val LOCK = Any()

        //database singleton , we have synchronized the db to have only 1 thread accessing it at once
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDB(context).also {
                instance = it
            }
        }

        private fun buildDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PupDatabase::class.java, "pupdatabase"
        ).build()
    }
}