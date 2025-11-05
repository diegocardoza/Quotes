package com.example.quotes.presentation.quotes.model.mappers

import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.presentation.quotes.model.QuoteItem

fun QuoteModel.toQuoteItem(): QuoteItem =
    QuoteItem(id = this.id, author = this.author, quote = "\"${this.quote}\"")