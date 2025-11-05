package com.example.quotes.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.domain.use_cases.FetchQuotesUseCase
import com.example.quotes.presentation.quotes.model.mappers.toQuoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val fetchQuotesUseCase: FetchQuotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuotesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        _uiState.update { it.copy(isLoading = true, isFetchError = false) }
        viewModelScope.launch {
            fetchQuotesUseCase()
                .onSuccess { quotes ->
                    val quotesList = quotes.map { it.toQuoteItem() }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quotes = quotesList
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isFetchError = true
                        )
                    }
                }
        }
    }

    fun changeQuoteToLeft() {
        val uiState = _uiState.value
        val hasError = uiState.isFetchError
        val quoteIndex = uiState.quoteIndex
        val quotes = uiState.quotes

        if (hasError) return
        _uiState.update {
            it.copy(
                quoteIndex =
                    if (quoteIndex == 0) quotes.size - 1
                    else quoteIndex - 1
            )
        }
    }

    fun changeQuoteToRight() {
        val uiState = _uiState.value
        val hasError = uiState.isFetchError
        val quoteIndex = uiState.quoteIndex
        val quotes = uiState.quotes

        if (hasError) return
        _uiState.update {
            it.copy(
                quoteIndex =
                    if (quoteIndex == quotes.size - 1) 0
                    else quoteIndex + 1
            )
        }
    }
}