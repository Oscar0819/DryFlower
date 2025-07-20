package com.oscar0819.core.data.di

import com.oscar0819.core.data.repo.AlbumsRepository
import dagger.Binds

abstract class DataModule {

    @Binds
    internal abstract fun bindsAlbumsRepository(
        albumsRepository:
    ): AlbumsRepository
}