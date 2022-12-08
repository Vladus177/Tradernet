package com.vladrusakov.tradernet.data.model

import com.vladrusakov.tradernet.domain.model.AssetModel

data class AssetEntry(
    val assetId: String?
)

fun AssetEntry.toDomain() : AssetModel = AssetModel(
    assetId = assetId.orEmpty()
)
