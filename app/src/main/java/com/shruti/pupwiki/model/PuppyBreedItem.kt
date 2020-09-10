package com.shruti.pupwiki.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class PuppyBreedItem(
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    val description: String?,
    val history: String?,
    @ColumnInfo(name = "breed_id")
    @PrimaryKey (autoGenerate = false)
    var id: Int?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @ColumnInfo(name = "breed_name")
    val name: String?,
    val origin: String?,
    val temperament: String?,
    @ColumnInfo(name = "breed_url")
    val url: String?,
)

data class PupPalette(var color : Int)