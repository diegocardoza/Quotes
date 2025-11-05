package com.example.quotes.presentation.quotes

import com.example.quotes.presentation.quotes.model.QuoteItem

data class QuotesUiState(
    val isLoading: Boolean = false,
    val quotes: List<QuoteItem> = emptyList(),
    val quoteIndex: Int = 0,
    val isFetchError: Boolean = false
)
