package com.example.quotes.data.network.datasource

import com.example.quotes.data.network.model.QuoteDTO

interface NetworkDatasource {

    suspend fun fetchQuotes(): Result<List<QuoteDTO>>

}