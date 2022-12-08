package com.vladrusakov.tradernet.data.remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vladrusakov.tradernet.data.model.TickerEntry
import com.vladrusakov.tradernet.data.remote.WebSocketApiImpl.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@ExperimentalCoroutinesApi
class WebSocketListener : WebSocketListener() {

    val socketEventChannel: Channel<SocketMessage> = Channel(20)

    private val gson = Gson()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        val json = gson.toJson(SocketParams())
        webSocket.send(json)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            //socketEventChannel.send(SocketMessage(text))
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        GlobalScope.launch {
            socketEventChannel.send(SocketMessage(exception = SocketAbortedException()))
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        socketEventChannel.close()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        GlobalScope.launch {
            socketEventChannel.send(SocketMessage(exception = t))
        }
    }

}

class SocketAbortedException : Exception()

data class SocketMessage(
    val tickers: List<TickerEntry>? = null,
    val exception: Throwable? = null
)

data class SocketParams(
    @SerializedName("quotes")
    val quotes: List<String> = listOf(
        "RSTI",
        "GAZP",
        "MRKZ",
        "RUAL",
        "HYDR",
        "MRKS",
        "SBER",
        "FEES",
        "TGKA",
        "VTBR",
        "ANH.US",
        "VICL.US",
        "BURG.US",
        "NBL.US",
        "YETI.US",
        "WSFS.US",
        "NIO.US",
        "DXC.US",
        "MIC.US",
        "HSBC.US",
        "EXPN.EU",
        "GSK.EU",
        "SHP.EU",
        "MAN.EU",
        "DB1.EU",
        "MUV2.EU",
        "TATE.EU",
        "KGF.EU",
        "MGGT.EU",
        "SGGD.EU"
    )
)