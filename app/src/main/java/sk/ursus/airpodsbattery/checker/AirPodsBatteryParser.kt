package sk.ursus.airpodsbattery.checker

class AirPodsBatteryParser {
    fun parse(scanResult: ScanResult): AirPodsBatteryLevel? {
        val data = scanResult.manufacturerSpecificData[76] ?: return null
        if (data.size != 27) return null

        val encodedData = data.encodeBase16()
        var left = parseBattery(encodedData[12])
        var right = parseBattery(encodedData[13])
        val case = parseBattery(encodedData[14])

        val flipped = encodedData[10] == 2.toByte()
        if (flipped) {
            val tmp = left
            left = right
            right = tmp
        }

        val id = encodedData.copyOfRange(6, 10)
        val isAirPodsPro2 = id.contentEquals(byteArrayOf(1, 4, 2, 0))

        return AirPodsBatteryLevel(left = left, right = right, case = case)
    }
}

private fun parseBattery(rawBatteryLevel: Byte): Int? {
    return if (rawBatteryLevel in 0..10) {
        (rawBatteryLevel * 10 + 4).coerceAtMost(100)
    } else {
        null
    }
}

private fun ByteArray.encodeBase16(): ByteArray {
    val encoded = mutableListOf<Byte>()
    for (byte in this) {
        val b = byte.toInt() and 0xFF
        encoded += (b ushr 4).toByte()
        encoded += (b and 15).toByte()
    }
    return encoded.toByteArray()
}