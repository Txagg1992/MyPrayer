package com.curiousapps.myprayer.ui.presentation
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.curiousapps.myprayer.ui.theme.AppDimens.ONE
import com.curiousapps.myprayer.ui.theme.AppDimens.SIX_FORTY
import com.curiousapps.myprayer.ui.theme.AppDimens.THREE_SIXTY
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen12Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen32Dp
import com.curiousapps.myprayer.ui.theme.MyPrayerTheme

@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel? = null,
    onNavigateToPrayer: (Int) -> Unit = {}
) {
    val resolvedViewModel = viewModel ?: hiltViewModel<RosaryViewModel>()
    val state by resolvedViewModel.state.collectAsState(RosaryViewModel.RosaryState())

    RosaryScreenContent(
        state = state,
        modifier = modifier,
        onPrayerClick = { index, scrollOffset ->
            resolvedViewModel.saveListScrollOffset(scrollOffset)
            onNavigateToPrayer(index)
        }
    )
}

@Composable
private fun RosaryScreenContent(
    state: RosaryViewModel.RosaryState,
    modifier: Modifier = Modifier,
    onPrayerClick: (index: Int, scrollOffset: Int) -> Unit = { _, _ -> }
) {
    val displayPrayerList = state.displayPrayers
    val isLoading = state.isLoading
    val error = state.error
    val listScrollState = rememberScrollState()
    val lastListScrollOffset = state.lastListScrollOffset

    LaunchedEffect(lastListScrollOffset, listScrollState.maxValue) {
        listScrollState.scrollTo(lastListScrollOffset.coerceAtMost(listScrollState.maxValue))
    }

    GradientBackground(primaryColor = Color.LightGray)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimen16Dp)
            .padding(bottom = dimen32Dp)
            .verticalScroll(listScrollState)
    ) {
        when {
            isLoading -> Text(text = stringResource(R.string.loading_prayers))
            error != null -> Text(text = "Error: $error")
            else -> {
                displayPrayerList.forEachIndexed { index, prayer ->
                    RosaryPrayerRow(
                        prayer = prayer,
                        stepNumber = index + ONE,
                        onClick = {
                            onPrayerClick(index, listScrollState.value)
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = dimen16Dp))
                }
            }
        }
    }
}


@Composable
private fun RosaryPrayerRow(
    prayer: RosaryPrayersItem,
    stepNumber: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimen12Dp, horizontal = dimen16Dp)
    ) {
        Text(
            text = "$stepNumber. ${prayer.prayerName}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true, widthDp = THREE_SIXTY, heightDp = SIX_FORTY)
@Composable
private fun RosaryScreenPreview() {
    MyPrayerTheme {
        RosaryScreenContent(
            state = RosaryViewModel.RosaryState(
                displayPrayers = listOf(
                    RosaryPrayersItem(1, "Sign of the Cross", ""),
                    RosaryPrayersItem(2, "Apostle's Creed", ""),
                    RosaryPrayersItem(3, "Lord's Prayer", ""),
                    RosaryPrayersItem(4, "Hail Mary", ""),
                    RosaryPrayersItem(5, "Glory Be", "")
                )
            )
        )
    }
}
