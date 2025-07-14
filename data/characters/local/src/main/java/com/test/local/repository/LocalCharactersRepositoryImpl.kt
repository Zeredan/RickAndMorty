package com.test.local.repository

import com.test.characters.model.Character
import com.test.local.room.internal.CharactersDAO
import com.test.local.room.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalCharactersRepositoryImpl @Inject constructor(
    private val charactersDAO: CharactersDAO
) : LocalCharactersRepository {
    override fun getCharactersAsFlow(): Flow<List<Character>> {
        return charactersDAO.getCharactersAsFlow().map {
            it.map { ch ->
                ch.toCharacter()
            }
        }
    }

    override fun updateCharacters(
        characters: List<Character>
    ) {
        charactersDAO.clearCharacters()
        charactersDAO.insertCharacters(characters.map { CharacterEntity(it) })
    }
}