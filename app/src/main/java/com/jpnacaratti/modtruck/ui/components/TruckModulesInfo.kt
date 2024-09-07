package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpnacaratti.modtruck.interfaces.TruckModule
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.nacaratti.modtruck.R
import kotlin.math.roundToInt

@Composable
fun TruckModulesInfo(truckViewModel: TruckViewModel, modifier: Modifier = Modifier) {

    val modules = listOf(
        ModuleData(
            module = truckViewModel.batteryLevelModule.value,
            icon = painterResource(R.drawable.battery_level_module),
            title = "Bateria",
            value = "${truckViewModel.batteryLevelModule.value.batteryLevel}%"
        ),
        ModuleData(
            module = truckViewModel.engineHealthModule.value,
            icon = painterResource(R.drawable.engine_health_module),
            title = "Vida útil do motor",
            value = "${truckViewModel.engineHealthModule.value.engineWear}%"
        ),
        ModuleData(
            module = truckViewModel.engineSoundModule.value,
            icon = painterResource(R.drawable.engine_sound_module),
            title = "Som do motor",
            value = truckViewModel.engineSoundModule.value.getCommonName()
        )
    )

    Column(modifier = modifier) {
        modules.filter { it.module.connected }.forEachIndexed { index, moduleData ->
            ModuleInfoComponent(
                icon = moduleData.icon,
                title = moduleData.title,
                value = moduleData.value,
                modifier = Modifier.fillMaxWidth()
            )

            if (index < modules.size - 1) {
                HorizontalDivider(
                    color = Gray,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 25.dp)
                )
            }
        }
    }
}

data class ModuleData(
    val module: TruckModule,
    val icon: Painter,
    val title: String,
    val value: String
)

@Preview
@Composable
private fun TruckModulesInfoPreview() {

    GoogleFontProvider.initialize()

    ModTruckTheme {
        TruckModulesInfo(TruckViewModel())
    }
}
