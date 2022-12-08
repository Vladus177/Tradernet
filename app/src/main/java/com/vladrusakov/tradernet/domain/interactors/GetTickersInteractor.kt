package com.vladrusakov.tradernet.domain.interactors

import com.vladrusakov.tradernet.data.remote.SocketMessage
import com.vladrusakov.tradernet.domain.repository.TickerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class GetTickersInteractor @Inject constructor(private val repository: TickerRepository) {
    @ExperimentalCoroutinesApi
    fun stopSocket() {
        repository.stopSocket()
    }

    @ExperimentalCoroutinesApi
    fun startSocket(): Channel<SocketMessage> = repository.startSocket()

}
