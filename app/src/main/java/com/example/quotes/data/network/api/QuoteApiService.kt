package com.example.quotes.data.network.api

import com.example.quotes.data.network.model.QuoteDTO
import retrofit2.http.GET

interface QuoteApiService {

    @GET("/quotes.json")
    suspend fun fetchQuotes(): List<QuoteDTO>

}