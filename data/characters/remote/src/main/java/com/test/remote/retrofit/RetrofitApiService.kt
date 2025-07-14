package com.test.remote.retrofit

import com.test.remote.retrofit.model.RickAndMortyResponseDTO
import retrofit2.http.GET

interface RetrofitApiService {
    @GET("character")
    suspend fun getRickAndMortyResponse() : RickAndMortyResponseDTO
}