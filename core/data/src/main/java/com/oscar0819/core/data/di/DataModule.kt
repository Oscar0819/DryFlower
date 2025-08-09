package com.oscar0819.core.data.di

import com.oscar0819.core.data.repo.SearchRepository
import com.oscar0819.core.data.repo.SearchSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsSearchRepository(
        searchRepository: SearchSearchRepositoryImpl
    ): SearchRepository
}