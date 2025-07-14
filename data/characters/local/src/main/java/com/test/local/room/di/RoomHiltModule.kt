package com.test.local.room.di

import android.content.Context
import androidx.room.Room
import com.test.local.room.internal.CharactersDAO
import com.test.local.room.internal.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomHiltModule {

    @Provides
    fun provideCharactersDatabase(
        @ApplicationContext appContext: Context
    ) : CharactersDatabase {
        return Room.databaseBuilder(
            appContext,
            CharactersDatabase::class.java,
            "characters_database"
        ).build()
    }
    @Provides
    fun provideCharacterDAO(
        database: CharactersDatabase
    ) : CharactersDAO {
        return database.charactersDao
    }
}