package com.test.remote.retrofit

import com.test.remote.retrofit.model.CharacterDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitApiService {
    @GET("/character")
    suspend fun getCharacters() : List<CharacterDTO>
}