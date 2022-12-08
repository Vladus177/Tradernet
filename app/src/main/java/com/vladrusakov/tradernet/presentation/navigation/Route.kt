package com.vladrusakov.tradernet.presentation.navigation

enum class Route(val route: String) {
    Home("home/root"),
    AssetDetail("home/details/{${AssetDetailArgs.Asset_ID}}"),
}
