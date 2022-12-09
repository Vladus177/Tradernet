package com.vladrusakov.tradernet.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vladrusakov.tradernet.presentation.model.Ticker

@Composable
fun TickerCell(
    modifier: Modifier = Modifier,
    ticker: Ticker,
    onClick: (Ticker) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = modifier.size(8.dp),
                model = "",
                contentDescription = "Ticker Image",
                contentScale = ContentScale.FillBounds
            )
            Text(text = "TBP")
        }

        Row(modifier = modifier.fillMaxWidth()) {
            Text(text = "TBP")
            Text(text = "TBP")
        }

    }
}

@Preview
@Composable
fun TickerCellPreview() {
    TickerCell(ticker = Ticker.mock())
}
