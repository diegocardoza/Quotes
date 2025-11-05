package com.example.quotes.data.network.datasource

import com.example.quotes.data.helper.QuoteDTOHelper
import com.example.quotes.data.network.api.QuoteApiService
import com.example.quotes.helper.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NetworkDatasourceImplTest {

    @get:Rule(order = 0)
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var quoteApiService: QuoteApiService

    lateinit var networkDatasource: NetworkDatasourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        networkDatasource = NetworkDatasourceImpl(
            quoteApiService = quoteApiService,
            ioDispatcher = StandardTestDispatcher()
        )
    }

    @Test
    fun `fetchQuotes returns success result when quoteApiService returns success response`() =
        runTest {
            // Given
            val expectedResponse = QuoteDTOHelper.buildQuoteDTOList().take(3)
            val expectedResult = Result.success(expectedResponse)
            coEvery { quoteApiService.fetchQuotes() } returns expectedResponse

            // When
            val capturedResult = networkDatasource.fetchQuotes()

            // Then
            assertThat(capturedResult).isEqualTo(expectedResult)
        }

    @Test
    fun `fetchQuotes returns failure result when quoteApiService returns an_exception`() = runTest {
        // Given
        coEvery { quoteApiService.fetchQuotes() } throws Exception("Http client error")

        // When
        val capturedResult = networkDatasource.fetchQuotes()

        // Then
        assertThat(capturedResult.isFailure).isTrue()
    }

}