package com.example.quotes.di

import com.example.quotes.data.network.api.QuoteApiService
import com.example.quotes.data.network.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Singleton
    @Provides
    fun provideMockWebServer(): MockWebServer = MockWebServer()


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private fun baseUrl(mockWebServer: MockWebServer): String =
        runBlocking(Dispatchers.IO) {
            mockWebServer.url("/").toString()
        }

    @Singleton
    @Provides
    fun providesRetrofit(mockWebServer: MockWebServer, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesQuoteApiService(retrofit: Retrofit): QuoteApiService =
        retrofit.create(QuoteApiService::class.java)

}