package com.example.quotes.presentation.quotes

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quotes.R
import com.example.quotes.domain.model.QuoteModel
import com.example.quotes.presentation.core.ui.theme.QuotesTheme
import com.example.quotes.presentation.quotes.model.mappers.toQuoteItem
import com.example.quotes.presentation.quotes.component.ErrorMessageLayout
import com.example.quotes.presentation.quotes.component.LoadingIndicator
import com.example.quotes.presentation.quotes.component.QuoteLayout

const val containerLayoutTag = "containerLayout"
const val appNameLayoutTag = "appNameLayout"
const val loadingIndicatorTag = "loadingIndicator"
const val errorMessageLayoutTag = "errorMessageLayout"
const val quoteLayoutTag = "quoteLayout"

@Composable
fun QuotesScreen(
    viewModel: QuoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuotesScreen(
        uiState = uiState,
        changeQuoteToLeft = viewModel::changeQuoteToLeft,
        changeQuoteToRight = viewModel::changeQuoteToRight
    )
}

@Composable
fun QuotesScreen(
    uiState: QuotesUiState,
    changeQuoteToLeft: () -> Unit,
    changeQuoteToRight: () -> Unit
) {
    val context = LocalContext.current
    var totalDrag by remember { mutableFloatStateOf(0f) }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { totalDrag = 0f },
                        onHorizontalDrag = { _, dragAmount ->
                            totalDrag += dragAmount
                        },
                        onDragEnd = {
                            when {
                                totalDrag < -50 -> changeQuoteToRight()
                                totalDrag > 50 -> changeQuoteToLeft()
                            }
                        }
                    )
                }
                .testTag(containerLayoutTag),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(appNameLayoutTag),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = context.getString(R.string.app_name),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )
            }
            when {
                uiState.isLoading -> LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(loadingIndicatorTag)
                )

                uiState.isFetchError -> ErrorMessageLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(errorMessageLayoutTag),
                    errorMessage = stringResource(R.string.fetch_error_message)
                )

                else -> QuoteLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(quoteLayoutTag),
                    quoteItem = uiState.quotes[uiState.quoteIndex]
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuotesScreenLoadingStatePrev() {
    QuotesTheme(dynamicColor = false) {
        val loadingState = QuotesUiState(
            isLoading = true,
            isFetchError = false,
            quotes = emptyList(),
            quoteIndex = 0
        )
        QuotesScreen(
            uiState = loadingState,
            changeQuoteToLeft = {},
            changeQuoteToRight = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuotesScreenErrorStatePrev() {
    QuotesTheme(dynamicColor = false) {
        val errorState = QuotesUiState(
            isLoading = false,
            isFetchError = true,
            quotes = emptyList(),
            quoteIndex = 0
        )
        QuotesScreen(
            uiState = errorState,
            changeQuoteToLeft = {},
            changeQuoteToRight = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuotesScreenSuccessStatePrev() {
    QuotesTheme(dynamicColor = false) {
        val successState = QuotesUiState(
            isLoading = false,
            isFetchError = false,
            quotes = listOf(
                QuoteModel(
                    id = 1,
                    author = "Diego Cardoza",
                    quote = "No tienes que ser él mejor de todos, tienes que ser tú pero en tú mejor versión"
                ).toQuoteItem()
            ),
            quoteIndex = 0
        )
        QuotesScreen(
            uiState = successState,
            changeQuoteToLeft = {},
            changeQuoteToRight = {}
        )
    }
}
