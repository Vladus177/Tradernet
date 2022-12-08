package com.vladrusakov.tradernet.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.vladrusakov.tradernet.presentation.screens.assetdetail.AssetDetailScreen
import com.vladrusakov.tradernet.presentation.screens.home.HomeScreen
import com.vladrusakov.tradernet.util.Constants.LEFT_SLIDE
import com.vladrusakov.tradernet.util.Constants.PRIMARY_FADE_SPEED
import com.vladrusakov.tradernet.util.Constants.PRIMARY_SLIDE_SPEED
import com.vladrusakov.tradernet.util.Constants.RIGHT_SLIDE
import com.vladrusakov.tradernet.util.Constants.SECONDARY_SLIDE_SPEED
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalAnimationApi
@Composable
fun NavigationComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
) {
    LaunchedEffect("navigation") {
        navigator.navFlow.onEach { navTarget ->
            if (navTarget.shouldPop) {
                navTarget.optionalArgs?.let { navResultArgs ->
                    navController.previousBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
                        navResultArgs.forEach { argsMapEntry ->
                            savedStateHandle[argsMapEntry.key] = argsMapEntry.value
                        }
                    }
                }
                if (navTarget.targetRoute == null) {
                    navController.popBackStack()
                } else {
                    val success =
                        navController.popBackStack(navTarget.targetRoute, navTarget.inclusive)
                    if (!success && navTarget.altRoute != null)
                        navController.popBackStack(navTarget.altRoute, navTarget.inclusive)
                }
            } else if (navTarget.targetRoute != null) {
                navController.navigate(navTarget.targetRoute) {
                    if (navTarget.clearBackstack) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }.launchIn(this)
    }
    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = modifier
    ) {
        composable(Route.Home.route) {
            HomeScreen(modifier = modifier)
        }
        composable(Route.AssetDetail.route,
            enterTransition = { enterRightToLeft() },
            popExitTransition = { exitLeftToRight() }) {
            AssetDetailScreen(modifier = modifier)
        }
    }
}

// Forward Navigation
private fun enterRightToLeft(
    offset: Int = LEFT_SLIDE,
    slideMillis: Int = PRIMARY_SLIDE_SPEED,
    fadeMillis: Int = PRIMARY_SLIDE_SPEED
): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { offset },
        animationSpec = tween(slideMillis)
    ) + fadeIn(animationSpec = tween(fadeMillis))
}

private fun exitLeftToRight(
    offset: Int = LEFT_SLIDE,
    slideMillis: Int = SECONDARY_SLIDE_SPEED,
    fadeMillis: Int = SECONDARY_SLIDE_SPEED
): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { offset },
        animationSpec = tween(slideMillis)
    ) + fadeOut(animationSpec = tween(fadeMillis))
}

// Back Navigation
private fun enterLeftToRight(
    offset: Int = RIGHT_SLIDE,
    slideMillis: Int = SECONDARY_SLIDE_SPEED,
    fadeMillis: Int = SECONDARY_SLIDE_SPEED
): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { offset },
        animationSpec = tween(slideMillis)
    ) + fadeIn(animationSpec = tween(fadeMillis))
}

private fun exitRightToLeft(
    offset: Int = RIGHT_SLIDE,
    slideMillis: Int = SECONDARY_SLIDE_SPEED,
    fadeMillis: Int = SECONDARY_SLIDE_SPEED
): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { offset },
        animationSpec = tween(slideMillis)
    ) + fadeOut(animationSpec = tween(fadeMillis))
}

private fun enterBottomToTop(
    slideMillis: Int = PRIMARY_SLIDE_SPEED,
    fadeMillis: Int = PRIMARY_FADE_SPEED
): EnterTransition {
    return expandVertically(
        animationSpec = tween(slideMillis),
        expandFrom = Alignment.Top,
    ) + fadeIn(animationSpec = tween(fadeMillis))
}

private fun exitTopToBottom(
    slideMillis: Int = PRIMARY_SLIDE_SPEED,
    fadeMillis: Int = PRIMARY_FADE_SPEED
): ExitTransition {
    return shrinkVertically(
        animationSpec = tween(slideMillis),
        shrinkTowards = Alignment.Top,
    ) + fadeOut(animationSpec = tween(fadeMillis))
}
