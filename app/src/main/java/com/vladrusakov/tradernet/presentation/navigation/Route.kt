package com.vladrusakov.tradernet.presentation.navigation

enum class Route(val route: String) {
    Home("home/root"),
    TickerDetail("home/details/{${TickerDetailArgs.TICKER_ID}}"),
}
