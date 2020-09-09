package com.shruti.pupwiki.service

import com.shruti.pupwiki.model.PuppyBreedItem
import io.reactivex.Single
import retrofit2.http.GET

interface PuppiesApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getPuppies(): Single<List<PuppyBreedItem>>
}