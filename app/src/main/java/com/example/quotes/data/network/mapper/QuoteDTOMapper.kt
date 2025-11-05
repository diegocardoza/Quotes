package com.example.quotes.data.network.mapper

import com.example.quotes.data.network.model.QuoteDTO
import com.example.quotes.domain.model.QuoteModel

fun QuoteDTO.toQuoteModel(): QuoteModel = QuoteModel(id = id, author = author, quote = quote)

fun List<QuoteDTO>.toQuoteModels(): List<QuoteModel> = map { it.toQuoteModel() }
