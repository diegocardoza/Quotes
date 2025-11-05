package com.example.quotes.data.network.datasource

import com.example.quotes.data.network.api.QuoteApiService
import com.example.quotes.data.network.di.IoDispatcher
import com.example.quotes.data.network.model.QuoteDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkDatasourceImpl @Inject constructor(
    private val quoteApiService: QuoteApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NetworkDatasource {
    override suspend fun fetchQuotes(): Result<List<QuoteDTO>> {
        return withContext(ioDispatcher) {
            try {
                val response = quoteApiService.fetchQuotes()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}