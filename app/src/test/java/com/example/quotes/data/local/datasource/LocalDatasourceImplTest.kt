package com.example.quotes.data.local.datasource

import com.example.quotes.data.local.daos.QuotesDao
import com.example.quotes.data.local.entities.QuoteEntity
import com.example.quotes.helper.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocalDatasourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var quotesDao: QuotesDao
    private lateinit var localDatasourceImpl: LocalDatasourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDatasourceImpl = LocalDatasourceImpl(
            quotesDao = quotesDao
        )
    }

    @Test
    fun `when fetchQuotes is called, it should return a list of QuoteEntity`() = runTest {
        // Given
        val expectedQuoteList = listOf(
            QuoteEntity(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            ),
            QuoteEntity(
                id = 2,
                author = "Author 2",
                quote = "Quote 2"
            )
        )
        coEvery { quotesDao.getQuotes() } returns expectedQuoteList

        // When
        val capturedQuoteList = localDatasourceImpl.fetchQuotes()

        // Then
        assertThat(capturedQuoteList).isEqualTo(expectedQuoteList)
        coVerify(exactly = 1) { quotesDao.getQuotes() }
    }

    @Test
    fun `when insertQuotes is called, it should call insertQuotes of DAO with the same data`() = runTest {
        // Given
        val listToInsert = listOf(
            QuoteEntity(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            ),
            QuoteEntity(
                id = 2,
                author = "Author 2",
                quote = "Quote 2"
            )
        )
        coEvery { quotesDao.insertQuotes(listToInsert) } returns Unit

        // When
        localDatasourceImpl.insertQuotes(listToInsert)

        // Then
        coVerify(exactly = 1) { quotesDao.insertQuotes(listToInsert) }
    }

}