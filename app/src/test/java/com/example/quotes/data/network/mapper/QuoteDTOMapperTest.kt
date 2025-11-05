package com.example.quotes.data.network.mapper

import com.example.quotes.data.network.model.QuoteDTO
import com.example.quotes.domain.model.QuoteModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class QuoteDTOMapperTest {

    @Test
    fun `toQuoteModel returns a QuoteModel`() {
        // Given
        val quoteDTO = QuoteDTO(
            id = 1,
            author = "Author 1",
            quote = "Quote 1"
        )
        val expectedQuoteModel = QuoteModel(
            id = 1,
            author = "Author 1",
            quote = "Quote 1"
        )

        //When
        val capturedQuoteModel = quoteDTO.toQuoteModel()

        //Then
        assertThat(capturedQuoteModel).isEqualTo(expectedQuoteModel)
    }

    @Test
    fun `toQuoteModels returns a list of QuoteModel`() {
        // Given
        val quoteDTOs = listOf(
            QuoteDTO(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            ),
            QuoteDTO(
                id = 2,
                author = "Author 2",
                quote = "Quote 2"
            ),
            QuoteDTO(
                id = 3,
                author = "Author 3",
                quote = "Quote 3"
            ),
        )
        val expectedQuoteModels = listOf(
            QuoteModel(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            ),
            QuoteModel(
                id = 2,
                author = "Author 2",
                quote = "Quote 2"
            ),
            QuoteModel(
                id = 3,
                author = "Author 3",
                quote = "Quote 3"
            ),
        )

        //When
        val capturedQuoteModels = quoteDTOs.toQuoteModels()

        //Then
        assertThat(capturedQuoteModels).isEqualTo(expectedQuoteModels)
    }

}