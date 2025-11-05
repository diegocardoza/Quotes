package com.example.quotes.data.local.datasource

import com.example.quotes.data.local.entities.QuoteEntity

interface LocalDatasource {

    suspend fun fetchQuotes(): List<QuoteEntity>

    suspend fun insertQuotes(quotes: List<QuoteEntity>)

}