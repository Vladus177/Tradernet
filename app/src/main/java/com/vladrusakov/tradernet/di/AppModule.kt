package com.vladrusakov.tradernet.di

import com.vladrusakov.tradernet.presentation.navigation.Navigator
import com.vladrusakov.tradernet.presentation.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}
