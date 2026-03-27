package com.curiousapps.myprayer.ui.presentation
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.myprayer.data.RosaryPrayersItem

@Composable
fun RosaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel = hiltViewModel(),
    onNavigateToPrayer: (RosaryPrayersItem) -> Unit = {}
) {
    val state by viewModel.state.collectAsState(RosaryViewModel.RosaryState())
    val displayPrayerList = state.displayPrayers
    val isLoading = state.isLoading
    val error = state.error

    GradientBackground(
        primaryColor = Color.LightGray,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        when {
            isLoading -> Text(text = "Loading prayers...")
            error != null -> Text(text = "Error: $error")
            else -> {
                displayPrayerList.forEach { prayer ->
                    RosaryPrayerRow(
                        prayer = prayer,
                        onClick = { onNavigateToPrayer(prayer) }
                    )
                }
            }
        }
    }
}


@Composable
private fun RosaryPrayerRow(
    prayer: RosaryPrayersItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = prayer.prayerName,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = prayer.prayerText,
            modifier = Modifier.padding(top = 4.dp)
        )
        if (prayer.youtubeUrl.isNotBlank()) {
            Text(
                text = prayer.youtubeUrl,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

