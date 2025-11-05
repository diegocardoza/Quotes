package com.example.quotes.data.local.datasource

import com.example.quotes.data.local.daos.QuotesDao
import com.example.quotes.data.local.entities.QuoteEntity
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(
    private val quotesDao: QuotesDao
) : LocalDatasource {
    override suspend fun fetchQuotes(): List<QuoteEntity> = quotesDao.getQuotes()

    override suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quotesDao.insertQuotes(quotes)
    }
}