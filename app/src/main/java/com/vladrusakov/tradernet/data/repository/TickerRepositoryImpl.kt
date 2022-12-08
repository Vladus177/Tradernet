package com.vladrusakov.tradernet.data.repository

import com.vladrusakov.tradernet.data.remote.SocketMessage
import com.vladrusakov.tradernet.data.remote.WebSocketApi
import com.vladrusakov.tradernet.domain.repository.TickerRepository
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class TickerRepositoryImpl @Inject
constructor(private val webSocketApi: WebSocketApi) : TickerRepository {

    override fun startSocket(): Channel<SocketMessage> =
        webSocketApi.startTickerSocket()


    override fun stopSocket() {
        webSocketApi.stopTickerSocket()
    }
}
