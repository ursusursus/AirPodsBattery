package sk.ursus.airpodsbattery.checker

import kotlinx.coroutines.flow.*
import sk.ursus.airpodsbattery.checker.AirPodsBatteryCheckerState.*

class AirPodsBatteryChecker(
    private val bluetoothLeScanner: BluetoothLeScanner,
    private val parser: AirPodsBatteryParser
) {
    fun airPodsBatteryLevel(): Flow<AirPodsBatteryCheckerState> {
        return bluetoothLeScanner.scan()
            .mapNotNull(parser::parse)
            .map<AirPodsBatteryLevel, AirPodsBatteryCheckerState> { Connected(it) }
            .catch { Error(it) }
            .onStart { emit(Connecting) }
    }
}

sealed class AirPodsBatteryCheckerState {
    object Connecting : AirPodsBatteryCheckerState()
    data class Connected(val airPodsBatteryLevel: AirPodsBatteryLevel) : AirPodsBatteryCheckerState()
    data class Error(val throwable: Throwable) : AirPodsBatteryCheckerState()
}

data class AirPodsBatteryLevel(val left: Int?, val right: Int?, val case: Int?)