package info.javaway.spend_sense.root.model
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.categories
import spendsense.shared.generated.resources.events
import spendsense.shared.generated.resources.settings


data class BottomBarItem(
    val title: StringResource,
    val appTab: AppTab,
    val icon: ImageVector
) {
    companion object {
        fun getItems() = listOf(
            BottomBarItem(Res.string.events, AppTab.Events, Icons.Rounded.CalendarMonth),
            BottomBarItem(Res.string.categories, AppTab.Categories, Icons.Rounded.Folder),
            BottomBarItem(Res.string.settings, AppTab.Settings, Icons.Rounded.Settings),
        )
    }
}
