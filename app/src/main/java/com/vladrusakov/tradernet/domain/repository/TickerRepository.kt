package com.vladrusakov.tradernet.domain.repository

import com.vladrusakov.tradernet.data.remote.SocketMessage
import kotlinx.coroutines.channels.Channel

interface TickerRepository {
    fun startSocket(): Channel<SocketMessage>
    fun stopSocket()
}
