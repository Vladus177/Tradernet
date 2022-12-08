package com.vladrusakov.tradernet.domain.model

import java.math.BigDecimal

data class TickerModel(
    val ticker: String?,
    val latestTrade: String?,
    val nameOsSecurity: String?,
    val nameOsSecurityLatin: String?,
    val lastTradePrice: BigDecimal?,
    val lastTradePriceChangePoints: BigDecimal?,
    val lastTradePriceChangePercents: BigDecimal?
)
