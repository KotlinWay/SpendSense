package info.javaway.spend_sense.platform

actual class DeviceInfo actual constructor(){
    actual val osName = System.getProperty("os.name") ?: "Desktop"
    actual val osVersion = System.getProperty("os.version") ?: "unknown version"
    actual val model = "Desktop"
    actual val cpu = System.getProperty("os.arch") ?: "unknown arch"
    actual val screenWidth = 0
    actual val screenHeight = 0
    actual val screenDestiny = 0

    actual fun getSummary() =
        "osName: $osName\n" +
                "osVersion: $osVersion\n" +
                "model: $model\n" +
                "cpu: $cpu"

}