package info.javaway.spend_sense.root.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarViewDay
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp


import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.root.model.AppTab
import info.javaway.spend_sense.root.model.BottomBarItem
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun BoxScope.RootBottomBar(
    selectedTab: AppTab,
    clickOnTab: (AppTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(
                AppThemeProvider.colors.surface,
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            )
            .padding(8.dp)
    ) {
        BottomBarItem.getItems().forEach { item ->
            BottomBarItemView(item, item.appTab == selectedTab) {
                clickOnTab(item.appTab)
            }
        }
    }
}

@Composable
fun RowScope.BottomBarItemView(
    bottomBarItem: BottomBarItem,
    isSelected: Boolean,
    clickOnTab: () -> Unit
) {

    val foreground =
        if (isSelected) AppThemeProvider.colors.accent else AppThemeProvider.colors.onSurface
    Column(
        modifier = Modifier.weight(1f).padding(4.dp).clickable { clickOnTab() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = bottomBarItem.icon,
            contentDescription = stringResource(bottomBarItem.title),
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(foreground)
        )
        Text(
            stringResource(bottomBarItem.title), color = foreground,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}








