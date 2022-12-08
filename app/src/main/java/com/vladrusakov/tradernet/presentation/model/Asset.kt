package com.vladrusakov.tradernet.presentation.model

import androidx.annotation.Keep
import com.vladrusakov.tradernet.domain.model.AssetModel

@Keep
data class Asset(
    val assetId: String
) {
    companion object {
        fun mock() = Asset(assetId = "exampleId")
    }
}

fun AssetModel.toUi(): Asset = Asset(
    assetId = assetId
)
