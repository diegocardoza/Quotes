package com.example.quotes.data.repository

import com.example.quotes.data.local.datasource.LocalDatasource
import com.example.quotes.data.local.entities.QuoteEntity
import com.example.quotes.data.network.datasource.NetworkDatasource
import com.example.quotes.data.network.model.QuoteDTO
import com.example.quotes.domain.model.QuoteModel
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class QuoteRepositoryImplTest {

    @MockK
    private lateinit var localDatasource: LocalDatasource

    @MockK
    private lateinit var networkDatasource: NetworkDatasource

    private lateinit var repository: QuoteRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = QuoteRepositoryImpl(
            localDatasource = localDatasource,
            networkDatasource = networkDatasource
        )
    }

    @Test
    fun `fetchQuotes should cache network data and return network success`() = runTest {
        // Given
        val networkDTOs = listOf(QuoteDTO(id = 1, author = "Author 1", quote = "Quote 1"))
        val expectedModels = listOf(QuoteModel(id = 1, author = "Author 1", quote = "Quote 1"))
        val expectedEntities = listOf(QuoteEntity(id = 1, author = "Author 1", quote = "Quote 1"))
        val networkResult = Result.success(networkDTOs)
        coEvery { networkDatasource.fetchQuotes() } returns networkResult
        coEvery { localDatasource.insertQuotes(expectedEntities) } just runs

        //When
        val repositoryResult = repository.fetchQuotes()

        //Then
        coVerify(exactly = 1) { networkDatasource.fetchQuotes() }
        coVerify(exactly = 1) { localDatasource.insertQuotes(expectedEntities) }
        coVerify(exactly = 0) { localDatasource.fetchQuotes() }
        assertThat(repositoryResult.isSuccess).isTrue()
        assertThat(repositoryResult.getOrNull()).isEqualTo(expectedModels)
    }

    @Test
    fun `fetchQuotes should return success with local data when network returns failure `() = runTest {
        // Given
        val expectedModels = listOf(QuoteModel(id = 1, author = "Author 1", quote = "Quote 1"))
        val expectedEntities = listOf(QuoteEntity(id = 1, author = "Author 1", quote = "Quote 1"))
        val networkResult = Result.failure<List<QuoteDTO>>(Throwable(""))
        coEvery { networkDatasource.fetchQuotes() } returns networkResult
        coEvery { localDatasource.fetchQuotes() } returns expectedEntities

        //When
        val repositoryResult = repository.fetchQuotes()

        //Then
        coVerify(exactly = 1) { networkDatasource.fetchQuotes() }
        coVerify(exactly = 1) { localDatasource.fetchQuotes() }
        coVerify(exactly = 0) { localDatasource.insertQuotes(any()) }
        assertThat(repositoryResult.isSuccess).isTrue()
        assertThat(repositoryResult.getOrNull()).isEqualTo(expectedModels)
    }

    @Test
    fun `fetchQuotes should return failure when network returns failure and local returns emptyList`() = runTest {
        // Given
        val networkResult = Result.failure<List<QuoteDTO>>(Throwable(""))
        coEvery { networkDatasource.fetchQuotes() } returns networkResult
        coEvery { localDatasource.fetchQuotes() } returns emptyList()

        //When
        val repositoryResult = repository.fetchQuotes()

        //Then
        coVerify(exactly = 1) { networkDatasource.fetchQuotes() }
        coVerify(exactly = 1) { localDatasource.fetchQuotes() }
        coVerify(exactly = 0) { localDatasource.insertQuotes(any()) }
        assertThat(repositoryResult.isFailure).isTrue()
        assertThat(repositoryResult).isEqualTo(networkResult)
    }

}