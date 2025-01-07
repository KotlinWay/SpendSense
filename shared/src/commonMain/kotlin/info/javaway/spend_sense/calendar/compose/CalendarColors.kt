package info.javaway.spend_sense.calendar.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CalendarColors(
    val colorSurface: Color,
    val colorOnSurface: Color,
    val colorAccent: Color
) {
    companion object {
        val default = CalendarColors(
            colorSurface = Color(0xFF1C1C1C),
            colorOnSurface = Color(0xFFBFBFBF),
            colorAccent = Color(0xFFFFFFFF)
        )
    }
}

val LocalCalendarColors = staticCompositionLocalOf {
    CalendarColors.default
}
