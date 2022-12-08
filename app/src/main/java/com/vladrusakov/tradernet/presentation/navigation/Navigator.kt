package com.vladrusakov.tradernet.presentation.navigation

import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Navigator {
    val navFlow: Flow<NavTarget>
    val toastMessageFlow: Flow<ToastMessage>

    fun navigateTo(
        targetRoute: String,
        pathArgs: Map<String, String>? = null,
        optionalArgs: Map<String, String>? = null,
        clearBackstack: Boolean = false,
    )

    fun navigateTo(
        targetRoute: Route,
        pathArgs: Map<String, String>? = null,
        optionalArgs: Map<String, String>? = null,
        clearBackstack: Boolean = false
    )

    fun popUp(navResult: Map<String, String>? = null)

    fun popTo(
        targetRoute: Route?,
        altRoute: Route?,
        navResult: Map<String, String>? = null,
        clearBackstack: Boolean = false,
        inclusive: Boolean = false
    )

    fun postToastMessage(
        iconRes: Int? = null,
        title: String? = null,
        message: String? = null,
        duration: Long = 5_000L
    )
}

data class NavTarget(
    val intent: Intent? = null,
    val deepLink: Uri? = null,
    val targetRoute: String? = null,
    val altRoute: String? = null,
    val optionalArgs: Map<String, String?>? = null,
    val shouldPop: Boolean = false,
    val clearBackstack: Boolean = false,
    val inclusive: Boolean = false
)

data class ToastMessage(
    val iconRes: Int? = null,
    val title: String? = null,
    val message: String? = null,
    val durationMillis: Long = 5_000L
)

class NavigatorImpl : Navigator {

    private val _navSharedFlow = MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)
    override val navFlow: SharedFlow<NavTarget> = _navSharedFlow.asSharedFlow()

    private val _messageSharedFlow = MutableSharedFlow<ToastMessage>(extraBufferCapacity = 1)
    override val toastMessageFlow: Flow<ToastMessage> = _messageSharedFlow.asSharedFlow()

    override fun navigateTo(
        targetRoute: Route,
        pathArgs: Map<String, String>?,
        optionalArgs: Map<String, String>?,
        clearBackstack: Boolean
    ) {
        navigateTo(
            targetRoute = targetRoute.route,
            pathArgs = pathArgs,
            optionalArgs = optionalArgs,
            clearBackstack = clearBackstack
        )
    }

    override fun navigateTo(
        targetRoute: String,
        pathArgs: Map<String, String>?,
        optionalArgs: Map<String, String>?,
        clearBackstack: Boolean
    ) {
        val route = if (pathArgs != null || optionalArgs != null) {
            var result = targetRoute
            pathArgs?.forEach { arg ->
                result = result.replaceFirst("{${arg.key}}", arg.value)
            }
            optionalArgs?.forEach { arg ->
                result = result.replaceFirst("{${arg.key}}", arg.value)
            }
            result
        } else {
            targetRoute
        }

        _navSharedFlow.tryEmit(
            NavTarget(
                targetRoute = route,
                shouldPop = false,
                optionalArgs = optionalArgs,
                clearBackstack = clearBackstack
            )
        )
    }

    override fun popUp(navResult: Map<String, String>?) {
        _navSharedFlow.tryEmit(
            NavTarget(
                targetRoute = null,
                shouldPop = true,
                optionalArgs = navResult,
                clearBackstack = false,
                inclusive = false
            )
        )
    }

    override fun popTo(
        targetRoute: Route?,
        altRoute: Route?,
        navResult: Map<String, String>?,
        clearBackstack: Boolean,
        inclusive: Boolean
    ) {
        _navSharedFlow.tryEmit(
            NavTarget(
                targetRoute = targetRoute?.route,
                altRoute = altRoute?.route,
                shouldPop = true,
                optionalArgs = navResult,
                clearBackstack = clearBackstack,
                inclusive = inclusive
            )
        )
    }

    override fun postToastMessage(iconRes: Int?, title: String?, message: String?, duration: Long) {
        _messageSharedFlow.tryEmit(
            ToastMessage(
                iconRes = iconRes,
                title = title,
                message = message,
                durationMillis = duration
            )
        )
    }

}