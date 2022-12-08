package com.vladrusakov.tradernet.presentation.model

import androidx.annotation.Keep
import com.vladrusakov.tradernet.domain.model.TickerModel
import java.math.BigDecimal

@Keep
data class Ticker(
    val ticker: String?,
    val latestTrade: String?,
    val nameOsSecurity: String?,
    val nameOsSecurityLatin: String?,
    val lastTradePrice: BigDecimal?,
    val lastTradePriceChangePoints: BigDecimal?,
    val lastTradePriceChangePercents: BigDecimal?
) {
    companion object {
        fun mock() = Ticker(
            ticker = "AAPL.SPB",
            latestTrade = "SPBFOR",
            nameOsSecurity = "Apple Inc.",
            nameOsSecurityLatin = "Apple Inc.",
            lastTradePrice = BigDecimal(142.56),
            lastTradePriceChangePercents = BigDecimal(1.78),
            lastTradePriceChangePoints = BigDecimal(2.5)
        )
    }
}

fun TickerModel.toUi(): Ticker = Ticker(
    ticker = ticker,
    latestTrade = latestTrade,
    nameOsSecurity = nameOsSecurity,
    nameOsSecurityLatin = nameOsSecurityLatin,
    lastTradePrice = lastTradePrice,
    lastTradePriceChangePoints = lastTradePriceChangePoints,
    lastTradePriceChangePercents = lastTradePriceChangePercents
)
