package info.javaway.spend_sense.extensions

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromHex(hexString: String): Color {
    val cleanedHexString = hexString.replace("#", "")
    val hexColor = cleanedHexString.toLongOrNull(16) ?: error("Invalid hex color $hexString")
    val alpha = if(cleanedHexString.length == 8) (hexColor shr 24 and 0xFF) / 255f else 1f
    val red = (hexColor shr 16 and 0xFF) / 255f
    val green = (hexColor shr 8 and 0xFF) / 255f
    val blue = (hexColor and 0xFF) / 255f

    return Color(red = red, green = green, blue = blue, alpha = alpha)
}