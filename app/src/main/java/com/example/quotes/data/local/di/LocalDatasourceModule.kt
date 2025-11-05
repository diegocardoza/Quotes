package com.example.quotes.data.local.di

import com.example.quotes.data.local.datasource.LocalDatasource
import com.example.quotes.data.local.datasource.LocalDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDatasourceModule {

    @Binds
    @Singleton
    abstract fun provideLocalDatasource(impl: LocalDatasourceImpl): LocalDatasource

}