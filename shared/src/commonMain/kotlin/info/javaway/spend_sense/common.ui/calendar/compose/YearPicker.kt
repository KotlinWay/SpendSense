package info.javaway.spend_sense.common.ui.calendar.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun YearPicker(
    initialYear: Int,
    onDismissRequest: () -> Unit,
    onYearSelectListener: (Int) -> Unit,
    colors: CalendarColors
) {

    val yearsRange = getYearsRange(initialYear)
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(colors.colorSurface)
                .padding(16.dp)
                .height(220.dp)
        ) {
            LaunchedEffect(false) {
                coroutineScope.launch {
                    listState.scrollToItem(199)
                }
            }
            LazyColumn(state = listState) {
                for (year in yearsRange) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                year.toString(),
                                modifier = Modifier
                                    .width(120.dp)
                                    .clickable {
                                        onYearSelectListener(year)
                                        onDismissRequest()
                                    }
                                    .padding(8.dp),
                                style = TextStyle(
                                    color = colors.colorOnSurface,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Divider(
                                modifier = Modifier.width(110.dp),
                                color = colors.colorOnSurface.copy(alpha = 0.2f)
                            )
                        }

                    }
                }
            }
        }
    }

}

private fun getYearsRange(
    initialYear: Int,
    range: Int = 200
): IntRange {
    return initialYear - range..initialYear + range
}