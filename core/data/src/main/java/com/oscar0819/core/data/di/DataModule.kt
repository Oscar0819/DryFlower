package com.oscar0819.core.data.di

import com.oscar0819.core.data.repo.AlbumsRepository
import com.oscar0819.core.data.repo.AlbumsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsAlbumsRepository(
        albumsRepository: AlbumsRepositoryImpl
    ): AlbumsRepository
}