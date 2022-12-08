package com.vladrusakov.tradernet.data.model

import com.google.gson.annotations.SerializedName
import com.vladrusakov.tradernet.domain.model.TickerModel
import java.math.BigDecimal

data class TickerEntry(
    @SerializedName("c") val ticker: String?,
    @SerializedName("ltr") val latestTrade: String?,
    @SerializedName("name") val nameOsSecurity: String?,
    @SerializedName("name2") val nameOsSecurityLatin: String?,
    @SerializedName("ltp") val lastTradePrice: BigDecimal?,
    @SerializedName("chg") val lastTradePriceChangePoints: BigDecimal?,
    @SerializedName("pcp") val lastTradePriceChangePercents: BigDecimal?,
)

fun TickerEntry.toDomain(): TickerModel = TickerModel(
    ticker = ticker,
    latestTrade = latestTrade,
    nameOsSecurity = nameOsSecurity,
    nameOsSecurityLatin = nameOsSecurityLatin,
    lastTradePrice = lastTradePrice,
    lastTradePriceChangePoints = lastTradePriceChangePoints,
    lastTradePriceChangePercents = lastTradePriceChangePercents
)
