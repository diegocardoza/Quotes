package com.example.quotes.presentation.quotes

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.example.quotes.R
import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.presentation.quotes.model.mappers.toQuoteItem
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class QuotesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun when_state_is_loading_LoadingIndicator_is_displayed_and_QuoteLayout_hidden() {
        //Given
        val loadingState = QuotesUiState(
            isLoading = true,
            isFetchError = false,
            quotes = emptyList(),
            quoteIndex = 0
        )

        //When
        composeTestRule.setContent {
            QuotesScreen(
                uiState = loadingState,
                changeQuoteToLeft = {},
                changeQuoteToRight = {}
            )
        }

        //Then
        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertExists()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertDoesNotExist()
    }

    @Test
    fun when_state_has_error_ErrorMessage_is_displayed_and_QuoteLayout_and_LoadingIndicator_are_hidden() {
        //Given
        val errorState = QuotesUiState(
            isLoading = false,
            isFetchError = true,
            quotes = emptyList(),
            quoteIndex = 0
        )
        val expectedErrorText = composeTestRule.activity.getString(R.string.fetch_error_message)

        //When
        composeTestRule.setContent {
            QuotesScreen(
                uiState = errorState,
                changeQuoteToLeft = {},
                changeQuoteToRight = {}
            )
        }

        //Then
        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertExists()
        composeTestRule.onNodeWithText(expectedErrorText).assertExists()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertDoesNotExist()
    }

    @Test
    fun when_state_is_not_loading_it_has_not_fetch_error_and_it_has_quotes_QuoteLayout_is_displayed_and_LoadingIndicator_and_ErrorMessage_are_hidden() {
        //Given
        val quotesState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = listOf(
                QuoteModel(
                    id = 1,
                    author = "Author 1",
                    quote = "Quote 1"
                ).toQuoteItem()
            ),
            quoteIndex = 0
        )

        //When
        composeTestRule.setContent {
            QuotesScreen(
                uiState = quotesState,
                changeQuoteToLeft = {},
                changeQuoteToRight = {}
            )
        }

        //Then
        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertExists()
        composeTestRule.onNodeWithText("Author 1").assertExists()
        composeTestRule.onNodeWithText("\"Quote 1\"").assertExists()
    }

    @Test
    fun when_state_is_displaying_a_quote_and_onSwipe_from_right_to_left_into_QuoteScreen_should_call_changeQuoteToRight() {
        //Given
        var leftWasCalled = false
        var rightWasCalled = false
        val quotesState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = listOf(
                QuoteModel(
                    id = 1,
                    author = "Author 1",
                    quote = "Quote 1"
                ).toQuoteItem()
            ),
            quoteIndex = 0
        )
        composeTestRule.setContent {
            QuotesScreen(
                uiState = quotesState,
                changeQuoteToLeft = { leftWasCalled = true },
                changeQuoteToRight = { rightWasCalled = true }
            )
        }
        //When
        composeTestRule.onNodeWithTag(containerLayoutTag).performTouchInput {
            swipeLeft()
        }

        //Then
        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertExists()
        assertThat(leftWasCalled).isFalse()
        assertThat(rightWasCalled).isTrue()
    }

    @Test
    fun when_state_is_displaying_a_quote_and_onSwipe_from_left_to_right_into_QuoteScreen_should_call_changeQuoteToLeft() {
        //Given
        var leftWasCalled = false
        var rightWasCalled = false
        val quotesState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = listOf(
                QuoteModel(
                    id = 1,
                    author = "Author 1",
                    quote = "Quote 1"
                ).toQuoteItem()
            ),
            quoteIndex = 0
        )
        composeTestRule.setContent {
            QuotesScreen(
                uiState = quotesState,
                changeQuoteToLeft = { leftWasCalled = true },
                changeQuoteToRight = { rightWasCalled = true }
            )
        }
        //When
        composeTestRule.onNodeWithTag(containerLayoutTag).performTouchInput {
            swipeRight()
        }

        //Then
        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertExists()
        assertThat(leftWasCalled).isTrue()
        assertThat(rightWasCalled).isFalse()
    }

}