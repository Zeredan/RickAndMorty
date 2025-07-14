package com.test.local.room.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.local.room.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract val charactersDao: CharactersDAO
}