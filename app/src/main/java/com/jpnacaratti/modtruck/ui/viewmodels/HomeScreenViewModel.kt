package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.states.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onFirstCardBlurReady = {
                    _uiState.update { it.copy(isFirstCardBlurReady = true) }
                },
                onConnectButtonClick = {
                    _uiState.update {
                        it.copy(
                            isTruckConnected = !it.isTruckConnected,
                            isTruckInfo = TruckInfo(
                                "Laranja",
                                "ABC-1234",
                                "Scania 620S V8"
                            )
                        )
                    }
                },
                onTruckSignStyleChange = { textLayoutResult ->
                    _uiState.update {
                        if (textLayoutResult.didOverflowHeight) {
                            it.copy(
                                truckSignTextStyle = it.truckSignTextStyle.copy(
                                    fontSize = it.truckSignTextStyle.fontSize * 0.9
                                )
                            )
                        } else {
                            it.copy(
                                truckSignReadyToDraw = true
                            )
                        }
                    }
                },
                onTruckColorStyleChange = { textLayoutResult ->
                    _uiState.update {
                        if (textLayoutResult.didOverflowHeight) {
                            it.copy(
                                truckColorTextStyle = it.truckColorTextStyle.copy(
                                    fontSize = it.truckColorTextStyle.fontSize * 0.9
                                )
                            )
                        } else {
                            it.copy(
                                truckColorReadyToDraw = true
                            )
                        }
                    }
                }
            )
        }
    }

}