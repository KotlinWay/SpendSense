package info.javaway.spend_sense.platform

import android.content.res.Resources
import android.os.Build
import kotlin.math.roundToInt

actual class DeviceInfo actual constructor(){

    private val displayMetrics = Resources.getSystem().displayMetrics

    actual val osName = "Android"
    actual val osVersion = "${Build.VERSION.SDK_INT}"
    actual val model = "${Build.MANUFACTURER} ${Build.MODEL}"
    actual val cpu = Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown cpu"
    actual val screenWidth = displayMetrics.widthPixels
    actual val screenHeight = displayMetrics.heightPixels
    actual val screenDestiny = displayMetrics.density.roundToInt()

    actual fun getSummary() =
        "osName: $osName\n" +
                "osVersion: $osVersion\n" +
                "model: $model\n" +
                "cpu: $cpu\n" +
                "screenWidth: $screenWidth\n" +
                "screenHeight: $screenHeight\n" +
                "screenDestiny: $screenDestiny"

}