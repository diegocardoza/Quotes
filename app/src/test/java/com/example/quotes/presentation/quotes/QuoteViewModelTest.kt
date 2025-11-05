package com.example.quotes.presentation.quotes

import app.cash.turbine.test
import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.domain.use_cases.FetchQuotesUseCase
import com.example.quotes.helper.MainDispatcherRule
import com.example.quotes.presentation.quotes.model.mappers.toQuoteItem
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteViewModelTest {

    @get: Rule
    val dispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var fetchQuotesUseCase: FetchQuotesUseCase
    private lateinit var viewModel: QuoteViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetchQuotes failure updates state with error flag`() = runTest {
        // Given
        val expectedErrorState = QuotesUiState(
            isLoading = false,
            isFetchError = true,
            quotes = emptyList(),
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.failure(Throwable(""))

        //When
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //Then
        viewModel.uiState.test {
            val errorState = awaitItem()
            assertThat(errorState).isEqualTo(expectedErrorState)
        }

    }

    @Test
    fun `fetchQuotes success updates state with quotes`() = runTest {
        // Given
        val quoteList = listOf(
            QuoteModel(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            )
        )
        val expectedSuccessState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = quoteList.map { it.toQuoteItem() },
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.success(quoteList)

        //When
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //Then
        viewModel.uiState.test {
            // Success result
            val successState = awaitItem()
            assertThat(successState).isEqualTo(expectedSuccessState)
        }
    }

    @Test
    fun `changeQuoteToLeft does not change index when error exists`() = runTest {
        // Given
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = true,
            quotes = emptyList(),
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.failure(Throwable(""))
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToLeft()

        //Then
        viewModel.uiState.test {
            val errorState = awaitItem()
            assertThat(errorState).isEqualTo(expectedState)
        }
    }

    @Test
    fun `changeQuoteToRight does not change index when error exists`() = runTest {
        // Given
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = true,
            quotes = emptyList(),
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.failure(Throwable(""))
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToRight()

        //Then
        viewModel.uiState.test {
            val errorState = awaitItem()
            assertThat(errorState).isEqualTo(expectedState)
        }
    }

    @Test
    fun `changeQuoteToLeft changes quote index when error does not exist`() = runTest {
        // Given
        val quoteList = listOf(
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
            )
        )
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = quoteList.map { it.toQuoteItem() },
            quoteIndex = 2
        )
        coEvery { fetchQuotesUseCase() } returns Result.success(quoteList)
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToLeft()

        //Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isEqualTo(expectedState)
            assertThat(state.quotes[state.quoteIndex]).isEqualTo(
                QuoteModel(
                    id = 3,
                    author = "Author 3",
                    quote = "Quote 3"
                ).toQuoteItem()
            )
        }
    }

    @Test
    fun `changeQuoteToRight changes quote index when error does not exist`() = runTest {
        // Given
        val quoteList = listOf(
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
            )
        )
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = quoteList.map { it.toQuoteItem() },
            quoteIndex = 1
        )
        coEvery { fetchQuotesUseCase() } returns Result.success(quoteList)
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToRight()

        //Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isEqualTo(expectedState)
            assertThat(state.quotes[state.quoteIndex]).isEqualTo(
                QuoteModel(
                    id = 2,
                    author = "Author 2",
                    quote = "Quote 2"
                ).toQuoteItem()
            )
        }
    }

    @Test
    fun `changeQuoteToLeft does not change index when a quote exists`() = runTest {
        // Given
        val quoteList = listOf(
            QuoteModel(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            )
        )
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = quoteList.map { it.toQuoteItem() },
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.success(quoteList)
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToLeft()

        //Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isEqualTo(expectedState)
            assertThat(state.quotes[state.quoteIndex]).isEqualTo(
                QuoteModel(
                    id = 1,
                    author = "Author 1",
                    quote = "Quote 1"
                ).toQuoteItem()
            )
        }
    }

    @Test
    fun `changeQuoteToRight does not change index when a quote exists`() = runTest {
        // Given
        val quoteList = listOf(
            QuoteModel(
                id = 1,
                author = "Author 1",
                quote = "Quote 1"
            )
        )
        val expectedState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = quoteList.map { it.toQuoteItem() },
            quoteIndex = 0
        )
        coEvery { fetchQuotesUseCase() } returns Result.success(quoteList)
        viewModel = QuoteViewModel(
            fetchQuotesUseCase = fetchQuotesUseCase
        )

        //When
        viewModel.changeQuoteToRight()

        //Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isEqualTo(expectedState)
            assertThat(state.quotes[state.quoteIndex]).isEqualTo(
                QuoteModel(
                    id = 1,
                    author = "Author 1",
                    quote = "Quote 1"
                ).toQuoteItem()
            )
        }
    }

}