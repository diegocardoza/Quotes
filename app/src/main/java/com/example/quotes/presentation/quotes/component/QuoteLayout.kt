package com.example.quotes.presentation.quotes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotes.presentation.quotes.model.QuoteItem


const val quoteTextTag = "quoteText"
const val authorTextTag = "authorText"

@Composable
fun QuoteLayout(
    modifier: Modifier = Modifier,
    quoteItem: QuoteItem
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.testTag(quoteTextTag),
            text = quoteItem.quote,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            modifier = Modifier.testTag(authorTextTag),
            text = quoteItem.author,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}