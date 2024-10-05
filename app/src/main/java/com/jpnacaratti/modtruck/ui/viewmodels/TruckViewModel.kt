package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jpnacaratti.modtruck.enums.ModuleName
import com.jpnacaratti.modtruck.enums.ModuleStatus
import com.jpnacaratti.modtruck.models.BatteryLevelModule
import com.jpnacaratti.modtruck.models.EngineHealthModule
import com.jpnacaratti.modtruck.models.EngineSoundModule
import com.jpnacaratti.modtruck.models.ModuleAttribute
import com.jpnacaratti.modtruck.models.ModuleInfo
import com.jpnacaratti.modtruck.models.ModuleStats
import com.jpnacaratti.modtruck.models.ModuleStatusInfo
import com.jpnacaratti.modtruck.models.OilStatusModule
import com.jpnacaratti.modtruck.models.SmartBoxInfo
import com.jpnacaratti.modtruck.models.TruckInfo
import com.jpnacaratti.modtruck.ui.theme.Green
import com.jpnacaratti.modtruck.ui.theme.Red
import com.jpnacaratti.modtruck.ui.theme.Yellow
import com.jpnacaratti.modtruck.utils.UserPreferences
import com.nacaratti.modtruck.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TruckViewModel : ViewModel() {

    // TODO: Inject this using hilt and singleton
    private lateinit var preferences: UserPreferences

    // TODO: Remove these connect = true
    private val _batteryLevelModule =
        MutableStateFlow(BatteryLevelModule(connected = true, batteryLevel = 75, status = ModuleStatus.WARNING))
    val batteryLevelModule: StateFlow<BatteryLevelModule> = _batteryLevelModule.asStateFlow()

    private val _engineSoundModule = MutableStateFlow(EngineSoundModule(connected = false))
    val engineSoundModule: StateFlow<EngineSoundModule> = _engineSoundModule.asStateFlow()

    private val _engineHealthModule =
        MutableStateFlow(EngineHealthModule(connected = false, rpm = 3143, temperature = 91F))
    val engineHealthModule: StateFlow<EngineHealthModule> = _engineHealthModule.asStateFlow()

    private val _oilStatusModule =
        MutableStateFlow(OilStatusModule(connected = false, oilLevel = 50))
    val oilStatusModule: StateFlow<OilStatusModule> = _oilStatusModule.asStateFlow()

    private val _truckInfo = MutableStateFlow<TruckInfo?>(null)
    val truckInfo: StateFlow<TruckInfo?> = _truckInfo.asStateFlow()

    private val _smartBoxInfo = MutableStateFlow<SmartBoxInfo?>(null)
    val smartBoxInfo: StateFlow<SmartBoxInfo?> = _smartBoxInfo.asStateFlow()

    private val _truckConnected = MutableStateFlow(false)
    val truckConnected: StateFlow<Boolean> = _truckConnected.asStateFlow()

    private val _hasConnectedBefore = MutableStateFlow(false)
    val hasConnectedBefore: StateFlow<Boolean> = _hasConnectedBefore.asStateFlow()

    fun setTruckConnected(value: Boolean) {
        if (_truckConnected.value == value) {
            return
        }

        _truckConnected.value = value
        if (value && !_hasConnectedBefore.value) {
            preferences.saveHasConnectBefore(true)
            _hasConnectedBefore.value = true
        }
    }

    fun updateTruckInfo(truckInfo: TruckInfo?) {
        if (_truckInfo.value == truckInfo) {
            return
        }

        _truckInfo.value = truckInfo
    }

    fun updateHasConnectedBefore(value: Boolean) {
        _hasConnectedBefore.value = value
    }

    fun updateSmartBoxInfo(smartBoxInfo: SmartBoxInfo?) {
        if (_smartBoxInfo.value == smartBoxInfo) {
            return
        }

        preferences.saveSmartBoxModel(smartBoxInfo!!.model)
        preferences.saveSmartBoxSerial(smartBoxInfo.serial)
        preferences.saveSmartBoxNumPorts(smartBoxInfo.numPorts)
        preferences.saveSmartBoxUsedPorts(smartBoxInfo.usedPorts)

        _smartBoxInfo.value = smartBoxInfo
    }

    fun updateModuleInfo(moduleInfo: ModuleInfo?) {
        if (moduleInfo == null) return

        when(moduleInfo.module) {
            ModuleName.BATTERY_LEVEL.name -> {
                updateBatteryLevelModule(moduleInfo.info.toInt())
            }
            ModuleName.ENGINE_SOUND.name -> {
                updateSoundModule(ModuleStatus.valueOf(moduleInfo.info))
            }
            ModuleName.ENGINE_HEALTH.name -> {
                updateEngineHealthModule(moduleInfo.info.split(";")[0].toInt(), moduleInfo.info.split(";")[1].toFloat())
            }
            ModuleName.OIL_STATUS.name -> {
                updateOilStatusModule(moduleInfo.info.toInt())
            }
        }
    }

    private fun updateBatteryLevelModule(value: Int) {
        var status = ModuleStatus.OK
        if (value <= 70) {
            status = ModuleStatus.WARNING
        }
        if (value <= 30) {
            status = ModuleStatus.ERROR
        }

        _batteryLevelModule.value = BatteryLevelModule(
            connected = true,
            batteryLevel = value,
            status = status
        )
    }

    private fun updateOilStatusModule(value: Int) {
        var status = ModuleStatus.OK
        if (value <= 70) {
            status = ModuleStatus.WARNING
        }
        if (value <= 30) {
            status = ModuleStatus.ERROR
        }

        _oilStatusModule.value = OilStatusModule(
            connected = true,
            oilLevel = value,
            status = status
        )
    }

    private fun updateSoundModule(status: ModuleStatus) {
        _engineSoundModule.value = EngineSoundModule(
            connected = true,
            status = status
        )
    }

    private fun updateEngineHealthModule(rpm: Int, temperature: Float) {
        val cache = EngineHealthModule(
            rpm = rpm,
            temperature = temperature
        )

        var status = ModuleStatus.OK
        if (cache.engineWear <= 70) {
            status = ModuleStatus.WARNING
        }
        if (cache.engineWear <= 35) {
            status = ModuleStatus.ERROR
        }

        _engineHealthModule.value = EngineHealthModule(
            connected = true,
            rpm = rpm,
            temperature = temperature,
            status = status
        )
    }

    fun setPreferences(preferences: UserPreferences) {
        this.preferences = preferences
    }

    fun loadSmartBoxFromPreferences() {
        val model = preferences.getSmartBoxModel()
        val serial = preferences.getSmartBoxSerial()
        val numPorts = preferences.getSmartBoxNumPorts()
        val usedPorts = preferences.getSmartBoxUsedPorts()

        if (model == null || serial == null) return

        _smartBoxInfo.value = SmartBoxInfo(
            model = model,
            serial = serial,
            numPorts = numPorts,
            usedPorts = usedPorts
        )
    }

    fun getConnectedModulesCount(): Int {
        return listOf(
            batteryLevelModule.value.connected,
            engineSoundModule.value.connected,
            engineHealthModule.value.connected,
            oilStatusModule.value.connected
        ).count { it }
    }

    fun getActiveTimeFormatted(): String {

        // TODO: Get the real time from the truck subtract millis from it

        val finalTime = 5540000L

        val hours = finalTime / (1000 * 60 * 60)
        val minutes = (finalTime / (1000 * 60)) % 60
        val seconds = (finalTime / 1000) % 60

        return "${hours}hr ${minutes}m ${seconds}s"
    }

    fun getAllModulesStatusIcon(): Int {
        val statuses = listOf(
            batteryLevelModule.value.status,
            engineSoundModule.value.status,
            engineHealthModule.value.status,
            oilStatusModule.value.status
        )

        return when {
            ModuleStatus.ERROR in statuses -> R.drawable.icon_error
            ModuleStatus.WARNING in statuses -> R.drawable.icon_warning
            else -> R.drawable.icon_check
        }
    }

    fun getAllModulesStatusDescription(): String {
        val statuses = listOf(
            batteryLevelModule.value.status,
            engineSoundModule.value.status,
            engineHealthModule.value.status,
            oilStatusModule.value.status
        )

        return when {
            ModuleStatus.ERROR in statuses -> "Alguns módulos estão em situação crítica!"
            ModuleStatus.WARNING in statuses -> "Alguns módulos precisam de atenção!"
            else -> "Tudo em ordem com o seu caminhão"
        }
    }

    fun getDrivenHours(): Int {
        return 550 // TODO: Get real driven hours
    }

    fun getAllModulesDetails(): List<ModuleStats> {
        val toR = mutableListOf<ModuleStats>()

        if (batteryLevelModule.value.connected) {
            val information =
                "Esse módulo tem como objetivo medir o nível da bateria dentro do caminhão Scania, ele pode exibir: 25%, 50%, 75% ou 100%."

            val status = getBatteryLevelStatusInfo()

            val stats = ModuleStats(
                icon = R.drawable.battery_level_module,
                title = "Bateria",
                showDescription = "${batteryLevelModule.value.batteryLevel}%",
                information = information,
                statusIcon = status.icon,
                statusIconColor = status.iconColor,
                statusLevel = status.messageLevel,
                statusDescription = status.description,
                attributes = getBatteryModuleAttributes()
            )

            toR.add(stats)
        }

        if (oilStatusModule.value.connected) {
            val information =
                "Esse módulo tem como objetivo medir o nível do óleo dentro do caminhão Scania."

            val status = getOilStatusInfo()

            val stats = ModuleStats(
                icon = R.drawable.oil_icon,
                title = "Óleo",
                showDescription = "${oilStatusModule.value.oilLevel}%",
                information = information,
                statusIcon = status.icon,
                statusIconColor = status.iconColor,
                statusLevel = status.messageLevel,
                statusDescription = status.description,
                attributes = getOilModuleAttributes()
            )

            toR.add(stats)
        }

        if (engineHealthModule.value.connected) {
            val information =
                "Este módulo é responsável por monitorar o desgaste do motor através da medição de RPM e temperatura. Ele alerta caso detecte um desgaste excessivo, garantindo que o motor opere dentro dos padrões seguros."

            val status = getEngineHealthStatusInfo()

            val stats = ModuleStats(
                icon = R.drawable.engine_health_module,
                title = "Vida útil do motor",
                showDescription = "${engineHealthModule.value.engineWear}%",
                information = information,
                statusIcon = status.icon,
                statusIconColor = status.iconColor,
                statusLevel = status.messageLevel,
                statusDescription = status.description,
                attributes = getEngineHealthAttributes()
            )

            toR.add(stats)
        }

        if (engineSoundModule.value.connected) {
            val information =
                "Este módulo tem como responsabilidade capturar o ruido sonoro produzido pelo motor e o analisar utilizando IA a fim de alertar caso o som esteja anormal."

            val status = getEngineSoundStatusInfo()

            val stats = ModuleStats(
                icon = R.drawable.engine_sound_module,
                title = "Som do motor",
                showDescription = engineSoundModule.value.getCommonName(),
                information = information,
                statusIcon = status.icon,
                statusIconColor = status.iconColor,
                statusLevel = status.messageLevel,
                statusDescription = status.description,
                attributes = emptyList()
            )

            toR.add(stats)
        }

        return toR
    }

    private fun getBatteryLevelStatusInfo(): ModuleStatusInfo {
        return when (batteryLevelModule.value.status) {
            ModuleStatus.ERROR -> ModuleStatusInfo(
                icon = R.drawable.icon_error,
                iconColor = Red,
                messageLevel = "Atenção: ",
                description = "Bateria do caminhão extremamente BAIXA!"
            )

            ModuleStatus.WARNING -> ModuleStatusInfo(
                icon = R.drawable.icon_warning,
                iconColor = Yellow
            )

            else -> ModuleStatusInfo(
                icon = R.drawable.icon_check,
                iconColor = Green
            )
        }
    }

    private fun getOilStatusInfo(): ModuleStatusInfo {
        return when (batteryLevelModule.value.status) {
            ModuleStatus.ERROR -> ModuleStatusInfo(
                icon = R.drawable.icon_error,
                iconColor = Red,
                messageLevel = "Atenção: ",
                description = "Nível do óleo extremamente baixo!"
            )

            ModuleStatus.WARNING -> ModuleStatusInfo(
                icon = R.drawable.icon_warning,
                iconColor = Yellow
            )

            else -> ModuleStatusInfo(
                icon = R.drawable.icon_check,
                iconColor = Green
            )
        }
    }

    private fun getEngineSoundStatusInfo(): ModuleStatusInfo {
        return when (engineSoundModule.value.status) {
            ModuleStatus.WARNING -> ModuleStatusInfo(
                icon = R.drawable.icon_error,
                iconColor = Red,
                messageLevel = "Atenção: ",
                description = "Motor apresentando ruidos anormais, leve a um mecânico o mais rápido possível!"
            )

            else -> ModuleStatusInfo(
                icon = R.drawable.icon_check,
                iconColor = Green,
                description = "Sem ruidos anormais"
            )
        }
    }

    private fun getEngineHealthStatusInfo(): ModuleStatusInfo {
        return when (engineHealthModule.value.status) {
            ModuleStatus.ERROR -> ModuleStatusInfo(
                icon = R.drawable.icon_error,
                iconColor = Red,
                messageLevel = "Atenção: ",
                description = "Motor com ALTO nível de desgaste, leve a uma assistência IMEDIATAMENTE!"
            )

            ModuleStatus.WARNING -> ModuleStatusInfo(
                icon = R.drawable.icon_warning,
                iconColor = Yellow
            )

            else -> ModuleStatusInfo(
                icon = R.drawable.icon_check,
                iconColor = Green
            )
        }
    }

    private fun getBatteryModuleAttributes(): List<ModuleAttribute> {
        return listOf(
            ModuleAttribute(
                attribute = "Bateria: ",
                value = "${batteryLevelModule.value.batteryLevel}%"
            )
        )
    }

    private fun getOilModuleAttributes(): List<ModuleAttribute> {
        return listOf(
            ModuleAttribute(
                attribute = "Nível do óleo: ",
                value = "${oilStatusModule.value.oilLevel}%"
            )
        )
    }

    private fun getEngineHealthAttributes(): List<ModuleAttribute> {
        return listOf(
            ModuleAttribute(
                attribute = "RPM do motor: ",
                value = "${engineHealthModule.value.rpm}"
            ),
            ModuleAttribute(
                attribute = "Temperatura do motor: ",
                value = "${engineHealthModule.value.temperature}º C"
            )
        )
    }

}