package com.test.remote.retrofit.model

import com.test.characters.model.Character
import com.test.characters.model.Location
import com.test.characters.model.Origin

class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    constructor(character: Character) : this(
        character.id,
        character.name,
        character.status,
        character.species,
        character.type,
        character.gender,
        character.origin,
        character.location,
        character.image,
        character.episode,
        character.url,
        character.created
    )
    fun toCharacter() : Character {
        return Character(
            id,
            name,
            status,
            species,
            type,
            gender,
            origin,
            location,
            image,
            episode,
            url,
            created
        )
    }
}