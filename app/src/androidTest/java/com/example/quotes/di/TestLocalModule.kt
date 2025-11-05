package com.example.quotes.di

import android.app.Application
import androidx.room.Room
import com.example.quotes.data.local.AppDatabase
import com.example.quotes.data.local.daos.QuotesDao
import com.example.quotes.data.local.di.LocalModule
import com.example.quotes.data.network.api.QuoteApiService
import com.example.quotes.data.network.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
object TestLocalModule {

    @Provides
    @Singleton
    fun providesAppDatabase(app: Application): AppDatabase = Room.inMemoryDatabaseBuilder(
        context = app,
        AppDatabase::class.java
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun providesQuotesDao(db: AppDatabase): QuotesDao = db.getQuoteDao()

}