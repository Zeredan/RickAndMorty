package com.test.remote.repository

import com.test.characters.model.Character
import kotlinx.coroutines.flow.Flow

interface RemoteCharactersRepository {
    suspend fun getCharacters() : List<Character>
    suspend fun getCharacterInfo(characterId: Int) : Character
}