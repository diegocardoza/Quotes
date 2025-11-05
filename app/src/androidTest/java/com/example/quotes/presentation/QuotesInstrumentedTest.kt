package com.example.quotes.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quotes.R
import com.example.quotes.data.fromJson
import com.example.quotes.data.server.MockWebServerRule
import com.example.quotes.presentation.quotes.appNameLayoutTag
import com.example.quotes.presentation.quotes.errorMessageLayoutTag
import com.example.quotes.presentation.quotes.loadingIndicatorTag
import com.example.quotes.presentation.quotes.quoteLayoutTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QuotesInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockRule = MockWebServerRule()

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun quoteLayout_is_displayed_when_success() {
        mockRule.server.enqueue(MockResponse().fromJson("quote_list_response.json"))

        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertExists()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag(quoteLayoutTag)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(errorMessageLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithText("Desarrollador Ágil").assertExists()
        composeTestRule.onNodeWithText("\"El código limpio es la mejor documentación.\"")
            .assertExists()
    }

    @Test
    fun errorMessageLayout_is_displayed_when_error_exists() {
        val errorString = composeTestRule.activity.getString(R.string.fetch_error_message)
        mockRule.server.enqueue(MockResponse().fromJson("quote_list_error_response.json"))

        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertExists()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag(errorMessageLayoutTag)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithTag(appNameLayoutTag).assertExists()
        composeTestRule.onNodeWithTag(loadingIndicatorTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(quoteLayoutTag).assertDoesNotExist()
        composeTestRule.onNodeWithText(errorString).assertExists()
    }

}