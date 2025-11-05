package com.example.quotes.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.quotes.data.local.AppDatabase
import com.example.quotes.data.local.daos.QuotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    const val APP_DATABASE_NAME = "app_database"

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesQuotesDao(db: AppDatabase): QuotesDao = db.getQuoteDao()

}