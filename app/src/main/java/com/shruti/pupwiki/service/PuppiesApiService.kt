package com.shruti.pupwiki.service

import com.shruti.pupwiki.model.PuppyBreedItem
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PuppiesApiService {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    val interceptor = HttpLoggingInterceptor()
    private fun getApi(): PuppiesApi {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // all network communications
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // gives observables
            .build()
            .create(PuppiesApi::class.java)
    }

    fun getPuppies(): Single<List<PuppyBreedItem>> {  // rxjava converts Gson to Single which emits data only once
        return getApi().getPuppies()
    }
}