package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jpnacaratti.modtruck.models.TruckInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val isTruckConnected: Boolean = false,
    val isBlurReady: Boolean = false,
    val isTruckInfo: TruckInfo? = null,
    val onBlurReady: () -> Unit = {},
    val onConnectButtonClick: () -> Unit = {}
)

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onBlurReady = {
                    _uiState.update { it.copy(isBlurReady = true) }
                },
                onConnectButtonClick = {
                    _uiState.update { it.copy(isTruckConnected = !it.isTruckConnected) }
                }
            )
        }
    }

}