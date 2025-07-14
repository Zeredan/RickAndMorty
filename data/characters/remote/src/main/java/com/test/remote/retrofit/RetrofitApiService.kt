package com.test.remote.retrofit

import com.test.remote.retrofit.model.CharacterDTO
import com.test.remote.retrofit.model.RickAndMortyResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {
    @GET("character")
    suspend fun getRickAndMortyResponse() : RickAndMortyResponseDTO
    @GET("character/{id}")
    suspend fun getCharacterInfo(@Path("id") id: Int) : CharacterDTO
}