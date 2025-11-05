package com.example.quotes.data.repository

import com.example.quotes.data.local.datasource.LocalDatasource
import com.example.quotes.data.local.entities.toQuoteEntities
import com.example.quotes.data.local.entities.toQuoteModels
import com.example.quotes.data.network.datasource.NetworkDatasource
import com.example.quotes.data.network.mapper.toQuoteModels
import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.domain.repository.QuoteRepository
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val networkDatasource: NetworkDatasource
) : QuoteRepository {

    override suspend fun fetchQuotes(): Result<List<QuoteModel>> {
        val result =
            networkDatasource.fetchQuotes().map { it.toQuoteModels() }
        return if (result.isSuccess) {
            result.getOrNull()?.let { quotes ->
                localDatasource.insertQuotes(quotes.toQuoteEntities())
            }
            result
        } else {
            val quotesFromLocalStorage = localDatasource.fetchQuotes().toQuoteModels()
            if (quotesFromLocalStorage.isNotEmpty()) {
                Result.success(quotesFromLocalStorage)
            } else {
                result
            }
        }
    }

}