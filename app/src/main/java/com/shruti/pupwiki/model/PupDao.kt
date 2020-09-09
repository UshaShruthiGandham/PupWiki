package com.shruti.pupwiki.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PupDao {
    @Insert
    suspend fun insertAll(vararg puppies : PuppyBreedItem) : List<Long>

    @Query ("SELECT * FROM puppybreeditem")
    suspend fun getAllPups() : List<PuppyBreedItem>

    @Query ("SELECT * FROM puppybreeditem WHERE breed_id = :pupId")
    suspend fun getPup(pupId: Int) : PuppyBreedItem

    @Query ("DELETE FROM puppybreeditem")
    suspend fun deleteAllPups()
}