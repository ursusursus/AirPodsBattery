package sk.ursus.airpodsbattery

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import sk.ursus.airpodsbattery.checker.AirPodsBatteryChecker
import sk.ursus.airpodsbattery.checker.AirPodsBatteryParser
import sk.ursus.airpodsbattery.checker.AndroidBluetoothLeScanner
import sk.ursus.airpodsbattery.ui.AirPodsBatterTheme
import sk.ursus.airpodsbattery.ui.MainScreen

private const val REQUEST_CODE = 123

private val BLUETOOTH_PERMISSIONS = arrayOf(
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_CONNECT,
    Manifest.permission.ACCESS_FINE_LOCATION
)

class MainActivity : ComponentActivity() {

    private lateinit var batteryChecker: AirPodsBatteryChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Default", "MainActivity # onCreate")
        batteryChecker = createBatteryChecker(this)

        setContent {
            AirPodsBatterTheme {
                MainScreen(batteryChecker = batteryChecker)
            }
        }
    }

//    private fun scan() {
//        if (!hasPermissions(BLUETOOTH_PERMISSIONS)) {
//            requestPermissions(BLUETOOTH_PERMISSIONS, REQUEST_CODE)
//            return
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == REQUEST_CODE) {
//            val granted = grantResults.all { it.isGranted() }
//            if (granted) {
//                scan()
//            }
//        }
//    }
}

private fun Activity.hasPermissions(permissions: Array<String>): Boolean {
    return permissions.all { ActivityCompat.checkSelfPermission(this, it).isGranted() }
}

private fun Int.isGranted(): Boolean {
    return this == PackageManager.PERMISSION_GRANTED
}

private fun createBatteryChecker(context: Context): AirPodsBatteryChecker {
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    return AirPodsBatteryChecker(
        bluetoothLeScanner = AndroidBluetoothLeScanner(bluetoothManager),
        parser = AirPodsBatteryParser()
    )
}
