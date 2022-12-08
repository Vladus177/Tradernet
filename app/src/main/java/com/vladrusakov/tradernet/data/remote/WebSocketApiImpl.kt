package com.vladrusakov.tradernet.data.remote

import com.vladrusakov.tradernet.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class WebSocketApiImpl @Inject constructor(
    private val socketOkHttpClient: OkHttpClient
) : WebSocketApi {


    @ExperimentalCoroutinesApi
    private var _webSocketListener: WebSocketListener? = null
    private var _webSocket: WebSocket? = null

    override fun startTickerSocket(): Channel<SocketMessage> =
        with(WebSocketListener()) {
            startTickerSocket(this)
            this@with.socketEventChannel
        }

    override fun startTickerSocket(webSocketListener: WebSocketListener) {
        _webSocketListener = webSocketListener
        _webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url(BuildConfig.WS_URL).build(),
            webSocketListener
        )
        socketOkHttpClient.dispatcher.executorService.shutdown()
    }

    override fun stopTickerSocket() {
        try {
            _webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            _webSocket = null
            _webSocketListener?.socketEventChannel?.close()
            _webSocketListener = null
        } catch (ex: Exception) {

        }
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }
}
