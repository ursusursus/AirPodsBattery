package sk.ursus.airpodsbattery.checker

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.util.Log
import android.util.SparseArray
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

interface BluetoothLeScanner {
    fun scan(): Flow<ScanResult>
}

class AndroidBluetoothLeScanner(
    private val bluetoothManager: BluetoothManager
) : BluetoothLeScanner {
    override fun scan(): Flow<ScanResult> {
        val bluetoothLeScanner = bluetoothManager.adapter.bluetoothLeScanner ?: return emptyFlow()
        return bluetoothLeScanner
            .scan(
                scanFilter = createScanFilter(),
                settings = createScanSettings()
            )
            .map { it.toScanResult() }
    }
}

class ScanResult(
    val rssi: Int,
    val manufacturerSpecificData: Map<Int, ByteArray>
)

private fun createScanFilter(): ScanFilter {
    // Can't be bothered to wrap
    val manufacturerData = ByteArray(27)
    val manufacturerDataMask = ByteArray(27)
    manufacturerData[0] = 7
    manufacturerData[1] = 25
    manufacturerDataMask[0] = -1
    manufacturerDataMask[1] = -1

    return ScanFilter.Builder()
        .setManufacturerData(76, manufacturerData, manufacturerDataMask)
        .build()
}

private fun createScanSettings(): ScanSettings {
    // Can't be bothered to wrap
    return ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .setReportDelay(1) // 0 nefunguje
        .build()
}

@SuppressLint("MissingPermission")
private fun android.bluetooth.le.BluetoothLeScanner.scan(
    scanFilter: ScanFilter,
    settings: ScanSettings
): Flow<android.bluetooth.le.ScanResult> {
    return callbackFlow {
        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult) {
                trySend(result)
            }

            override fun onBatchScanResults(results: MutableList<android.bluetooth.le.ScanResult>) {
                results.forEach(::trySend)
            }

            override fun onScanFailed(errorCode: Int) {
                Log.d("Default", "errr happened=$errorCode")
                cancel()
            }
        }
        Log.d("Default", "scanning")
        startScan(listOf(scanFilter), settings, callback)
        awaitClose {
            Log.d("Default", "stopping")
            stopScan(callback)
        }
    }
}

private fun android.bluetooth.le.ScanResult.toScanResult() = ScanResult(
    rssi = rssi,
    manufacturerSpecificData = scanRecord?.manufacturerSpecificData?.toMap() ?: emptyMap()
)

private fun <T> SparseArray<T>.toMap(): Map<Int, T> {
    val map = mutableMapOf<Int, T>()
    for (i in 0 until size()) {
        val key = keyAt(i)
        map[key] = get(key)
    }
    return map
}