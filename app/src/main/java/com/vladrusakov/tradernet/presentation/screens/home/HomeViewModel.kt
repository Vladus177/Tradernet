package com.vladrusakov.tradernet.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladrusakov.tradernet.domain.interactors.GetTickersInteractor
import com.vladrusakov.tradernet.presentation.model.Ticker
import com.vladrusakov.tradernet.presentation.navigation.Navigator
import com.vladrusakov.tradernet.presentation.navigation.Screen
import com.vladrusakov.tradernet.presentation.navigation.TickerDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeScreenUiState(
    val homeState: HomeUiState
)

sealed interface HomeUiState {
    data class Success(val tickers: List<Ticker>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

interface HomeEvents {
    fun getTickers()
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val getTickersInteractor: GetTickersInteractor,
    private val navigator: Navigator
) : ViewModel(), HomeEvents {

    private val _uiState = MutableStateFlow(HomeScreenUiState(HomeUiState.Loading))
    val uiState = _uiState.asStateFlow()

    override fun getTickers() {
        subscribeToSocketEvents()
    }

    @ExperimentalCoroutinesApi
    fun subscribeToSocketEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getTickersInteractor.startSocket().consumeEach {
                    if (it.exception == null) {
                        println("Collecting : $it")
                    } else {
                        onSocketError()
                    }
                }
            } catch (ex: Exception) {
                onSocketError()
            }
        }
    }

    private fun onSocketError() {
        _uiState.value = HomeScreenUiState(HomeUiState.Error)
    }

    override fun onCleared() {
        getTickersInteractor.stopSocket()
        super.onCleared()
    }

    fun navToTickerDetails(ticker: Ticker) {
        navigator.navigateTo(
            targetRoute = Screen.TickerDetail.route,
            pathArgs = mapOf(
                TickerDetailArgs.TICKER_ID to ticker.ticker.orEmpty(),
            ),
            optionalArgs = null,
            clearBackstack = false
        )
    }
}