package com.test.remote.repository

import com.test.characters.model.Character
import com.test.remote.retrofit.RetrofitApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteCharactersRepositoryImpl @Inject constructor(
    private val apiService: RetrofitApiService
) : RemoteCharactersRepository {
    override suspend fun getCharacters(): List<Character> {
        return apiService.getRickAndMortyResponse().results.map { it.toCharacter() }
    }

    override suspend fun getCharacterInfo(characterId: Int): Character {
        TODO()
    }

}