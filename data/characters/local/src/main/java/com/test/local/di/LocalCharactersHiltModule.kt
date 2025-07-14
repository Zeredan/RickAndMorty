package com.test.local.di

import android.content.Context
import androidx.room.Room
import com.test.local.repository.LocalCharactersRepository
import com.test.local.repository.LocalCharactersRepositoryImpl
import com.test.local.room.internal.CharactersDAO
import com.test.local.room.internal.CharactersDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class LocalCharactersHiltModule{
    @Binds
    abstract fun bindRepository(impl: LocalCharactersRepositoryImpl) : LocalCharactersRepository
}
