package com.test.local.room.internal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.local.room.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDAO {
    @Query("SELECT * FROM CharacterEntity")
    fun getCharactersAsFlow() : Flow<List<CharacterEntity>>

    @Query("SELECT * FROM CharacterEntity WHERE id = :idd")
    fun getCharacterByIdAsFlow(idd: Int) : Flow<CharacterEntity?>

    @Update
    fun updateCharacters(characters: List<CharacterEntity>)

    @Insert
    fun insertCharacters(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Query("DELETE FROM CharacterEntity")
    fun clearCharacters(): Int

/// Не будет использовано в силу другого подхода для фильтрования и поиска по имени. Из общей кучи всех героев в MainViewmodel будут реактивно при обновлении данных в бд, или самих фильтров, изменяться
//    @Query("SELECT * FROM CharacterEntity WHERE name LIKE :_name")
//    fun getCharactedsByName(_name: String): Flow<List<CharacterEntity>>

}