package com.example.quotes.domain.repository

import com.example.quotes.domain.model.QuoteModel

interface QuoteRepository {

    suspend fun fetchQuotes(): Result<List<QuoteModel>>

}