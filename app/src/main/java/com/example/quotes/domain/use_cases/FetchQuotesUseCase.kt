package com.example.quotes.domain.use_cases

import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.domain.repository.QuoteRepository
import javax.inject.Inject

open class FetchQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke(): Result<List<QuoteModel>> =
        repository.fetchQuotes()

}