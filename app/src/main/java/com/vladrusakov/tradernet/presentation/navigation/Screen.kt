package com.vladrusakov.tradernet.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vladrusakov.tradernet.R

enum class Screen(
    val route: Route,
    @StringRes val title: Int,
    @StringRes val contentDesc: Int,
    @DrawableRes val icon: Int? = null
) {
    Home(Route.Home, R.string.home_title, R.string.home_title),
    AssetDetail(Route.AssetDetail, R.string.asset_detail_title, R.string.asset_detail_title)
}
