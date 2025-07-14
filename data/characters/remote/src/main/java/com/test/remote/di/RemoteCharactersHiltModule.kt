package com.test.remote.di

import android.content.Context
import com.test.remote.repository.RemoteCharactersRepository
import com.test.remote.repository.RemoteCharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteCharactersHiltModule{
    @Binds
    abstract fun bindRepository(impl: RemoteCharactersRepositoryImpl) : RemoteCharactersRepository
}
