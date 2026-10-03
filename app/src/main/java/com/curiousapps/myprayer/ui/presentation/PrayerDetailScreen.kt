package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.myprayer.R

@Composable
fun PrayerDetailScreen(
    startIndex: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RosaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val prayers = state.displayPrayers
    var currentIndex by rememberSaveable { mutableIntStateOf(startIndex) }
    val prayer = prayers.getOrNull(currentIndex)
    val appBarContentColor = Color.DarkGray

    GradientBackground(primaryColor = Color.LightGray)

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back to list",
                    tint = appBarContentColor
                )
            }
            Text(
                text = prayer?.prayerName ?: "",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = appBarContentColor
            )
            Spacer(modifier = Modifier.size(48.dp))
        }

        Image(
            painter = painterResource(
                id = when {
                    prayer == null -> R.drawable.celtic_cross
                    prayer.prayerName == "Hail Mary" -> R.drawable.crucifix1
                    prayer.prayerName == "Lord's Prayer" -> R.drawable.cross_of_jesus
                    prayer.id < 0 -> R.drawable.easter_cross
                    else -> R.drawable.celtic_cross
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(text = "Error: ${state.error}")
                prayer != null -> {
                    Text(
                        text = prayer.prayerText,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                    if (prayer.youtubeUrl.isNotBlank()) {
                        Text(
                            text = prayer.youtubeUrl,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (currentIndex > 0) currentIndex-- },
                enabled = currentIndex > 0,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(text = "Previous")
            }
            Button(
                onClick = { if (currentIndex < prayers.size - 1) currentIndex++ },
                enabled = currentIndex < prayers.size - 1,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(text = "Next")
            }
        }
    }
}
