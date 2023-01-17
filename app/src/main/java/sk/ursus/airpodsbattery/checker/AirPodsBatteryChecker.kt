package sk.ursus.airpodsbattery.checker

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sk.ursus.airpodsbattery.checker.AirPodsBatteryCheckerState.*
import kotlin.random.Random

class AirPodsBatteryChecker(
    private val bluetoothLeScanner: BluetoothLeScanner,
    private val parser: AirPodsBatteryParser
) {
    private val _state = MutableStateFlow<AirPodsBatteryCheckerState>(
        Connected(
            AirPodsBatteryLevel(
                left = Random.nextInt(100),
                right = Random.nextInt(100),
                case = Random.nextInt(100)
            )
        )
    )

    init {
        GlobalScope.launch {
            flow {
                emit(AirPodsBatteryCheckerState.Connecting)
                delay(500)
                for (i in 0 until 100) {
                    delay(3000)
                    emit(
                        Connected(
                            AirPodsBatteryLevel(
                                left = Random.nextInt(100),
                                right = Random.nextInt(100),
                                case = Random.nextInt(100)
                            )
                        )
                    )
                }
            }.collect {
                Log.d("Default", "emitting=$it")
                _state.value = it
            }
        }
    }

    fun airPodsBatteryLevel(): Flow<AirPodsBatteryCheckerState> {
        return _state
//        return bluetoothLeScanner.scan()
//            .mapNotNull(parser::parse)
//            .map<AirPodsBatteryLevel, AirPodsBatteryCheckerState> { Connected(it) }
//            .catch { Error(it) }
//            .onStart { emit(Connecting) }
//        return flow {
//            emit(Connecting)
//            delay(500)
//            for (i in 0 until 100) {
//                emit(
//                    AirPodsBatteryCheckerState.Connected(
//                        AirPodsBatteryLevel(
//                            left = Random.nextInt(100),
//                            right = Random.nextInt(100),
//                            case = Random.nextInt(100)
//                        )
//                    )
//                )
//                delay(3000)
//            }
//        }
    }
}

sealed class AirPodsBatteryCheckerState {
    object Connecting : AirPodsBatteryCheckerState()
    data class Connected(val airPodsBatteryLevel: AirPodsBatteryLevel) : AirPodsBatteryCheckerState()
    data class Error(val throwable: Throwable) : AirPodsBatteryCheckerState()
}

data class AirPodsBatteryLevel(val left: Int?, val right: Int?, val case: Int?)