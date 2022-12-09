package com.vladrusakov.tradernet.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.vladrusakov.tradernet.presentation.navigation.Screen

@SuppressLint("ComposableNaming")
@Composable
fun onScreenStart(
    screen: Screen,
    start: () -> Unit,
    cleanUp: () -> Unit = {},
) {
    DisposableEffect(screen.route.name) {
        start()
        onDispose { cleanUp() }
    }
}

fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }
