package com.example.quotes.data.di

import com.example.quotes.data.repository.QuoteRepositoryImpl
import com.example.quotes.domain.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuotesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository

}