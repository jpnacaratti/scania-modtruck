package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.jpnacaratti.modtruck.models.TruckInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val isTruckConnected: Boolean = false,
    val isBlurReady: Boolean = false,
    val isTruckInfo: TruckInfo? = null,
    val truckColorTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckSignTextStyle: TextStyle = TextStyle(fontSize = 15.sp),
    val truckColorReadyToDraw: Boolean = false,
    val truckSignReadyToDraw: Boolean = false,
    val onBlurReady: () -> Unit = {},
    val onConnectButtonClick: () -> Unit = {},
    val onTruckSignStyleChange: (TextLayoutResult) -> Unit = {},
    val onTruckColorStyleChange: (TextLayoutResult) -> Unit = {}
) {
    fun getTruckQualityStatus(): Float {
        return 0.73f // TODO: Calc
    }
}

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