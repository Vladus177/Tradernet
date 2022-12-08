package com.vladrusakov.tradernet.data.remote

import kotlinx.coroutines.channels.Channel

interface WebSocketApi {
    fun startTickerSocket(): Channel<SocketMessage>
    fun startTickerSocket(webSocketListener: WebSocketListener)
    fun stopTickerSocket()
}