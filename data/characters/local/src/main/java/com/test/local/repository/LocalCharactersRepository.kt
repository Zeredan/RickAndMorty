package com.test.local.repository

import com.test.characters.model.Character
import com.test.local.room.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface LocalCharactersRepository {
    fun getCharactersAsFlow() : Flow<List<Character>>

    fun updateCharacters(characters: List<Character>)
}