package com.example.quotes.domain.use_cases

import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.domain.repository.QuoteRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchQuotesUseCaseTest {

    @MockK
    private lateinit var repository: QuoteRepository
    private lateinit var useCase: FetchQuotesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchQuotesUseCase(
            repository = repository
        )
    }

    @Test
    fun `useCase should return a success result when repository returns success result`() = runTest {
        //Given
        val expectedResult =
            Result.success(listOf(QuoteModel(id = 1, author = "Author 1", quote = "Quote 1")))
        coEvery { repository.fetchQuotes() } returns expectedResult

        //When
        val result = useCase()

        //Then
        coVerify(exactly = 1) { repository.fetchQuotes() }
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `useCase should return a failure result when repository returns failure result`() = runTest {
        //Given
        val expectedResult =
            Result.failure<List<QuoteModel>>(Throwable(""))
        coEvery { repository.fetchQuotes() } returns expectedResult

        //When
        val result = useCase()

        //Then
        coVerify(exactly = 1) { repository.fetchQuotes() }
        assertThat(result).isEqualTo(expectedResult)
    }

}