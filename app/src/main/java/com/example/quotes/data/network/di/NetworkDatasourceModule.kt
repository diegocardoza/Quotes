package com.example.quotes.data.network.di

import com.example.quotes.data.network.datasource.NetworkDatasource
import com.example.quotes.data.network.datasource.NetworkDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDatasourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkDatasource(impl: NetworkDatasourceImpl): NetworkDatasource

}