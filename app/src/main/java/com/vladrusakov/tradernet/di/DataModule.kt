package com.vladrusakov.tradernet.di

import com.vladrusakov.tradernet.data.remote.WebSocketApi
import com.vladrusakov.tradernet.data.remote.WebSocketApiImpl
import com.vladrusakov.tradernet.data.repository.TickerRepositoryImpl
import com.vladrusakov.tradernet.domain.repository.TickerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Singleton
    @Provides
    fun provideWebSocketApi(okHttpClient: OkHttpClient): WebSocketApi {
        return WebSocketApiImpl(socketOkHttpClient = okHttpClient)
    }

    @Singleton
    @Provides
    fun provideTickerRepository(webSocketApi: WebSocketApi): TickerRepository {
        return TickerRepositoryImpl(webSocketApi)
    }
}
