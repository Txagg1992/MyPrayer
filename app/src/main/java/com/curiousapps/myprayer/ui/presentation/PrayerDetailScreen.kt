package com.curiousapps.myprayer.ui.presentation

import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.AudioVoice
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.curiousapps.myprayer.ui.theme.AppDimens.ONE
import com.curiousapps.myprayer.ui.theme.AppDimens.ONE_HUNDRED
import com.curiousapps.myprayer.ui.theme.AppDimens.ONE_EIGHTY
import com.curiousapps.myprayer.ui.theme.AppDimens.SIX_FORTY
import com.curiousapps.myprayer.ui.theme.AppDimens.THREE_SIXTY
import com.curiousapps.myprayer.ui.theme.AppDimens.TWO_SIXTY
import com.curiousapps.myprayer.ui.theme.AppDimens.ZERO
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen120Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen220Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen24Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen48Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen72Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen8Dp
import com.curiousapps.myprayer.ui.theme.MyPrayerTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PrayerDetailScreen(
    startIndex: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    prayersOverride: List<RosaryPrayersItem>? = null,
    viewModel: RosaryViewModel? = null
) {
    val hapticFeedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val pageTransitionState = rememberPageTransitionState()
    val context = LocalContext.current
    val resolvedViewModel = viewModel ?: if (LocalInspectionMode.current) null else hiltViewModel<RosaryViewModel>()
    val state = resolvedViewModel?.state?.collectAsState()?.value
        ?: RosaryViewModel.RosaryState(displayPrayers = prayersOverride ?: emptyList())
    val prayers = prayersOverride ?: state.displayPrayers
    var currentIndex by rememberSaveable { mutableIntStateOf(startIndex) }
    val appBarContentColor = Color.DarkGray
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isAudioPlaying by remember { mutableStateOf(false) }

    GradientBackground(primaryColor = Color.LightGray)

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen8Dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = stringResource(R.string.back_to_list),
                    tint = appBarContentColor
                )
            }
            Text(
                text = prayers.getOrNull(currentIndex)?.prayerName ?: stringResource(R.string.blank_space),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .applyPageTransition(pageTransitionState),
                color = appBarContentColor
            )
            Spacer(modifier = Modifier.size(dimen48Dp))
        }

        val prayer = prayers.getOrNull(currentIndex)
        val selectedAudioName = when (state.audioVoice) {
            AudioVoice.MALE -> prayer?.audio?.male.orEmpty()
            AudioVoice.FEMALE -> prayer?.audio?.female.orEmpty()
        }
        val selectedAudioResId = if (selectedAudioName.isBlank()) {
            ZERO
        } else {
            context.resources.getIdentifier(selectedAudioName, "raw", context.packageName)
        }
        val isAudioButtonEnabled = state.audioEnabled && selectedAudioResId != ZERO

        LaunchedEffect(currentIndex, state.audioVoice, state.audioEnabled) {
            mediaPlayer?.release()
            mediaPlayer = null
            isAudioPlaying = false
        }

        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer?.release()
                mediaPlayer = null
                isAudioPlaying = false
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .applyPageTransition(pageTransitionState)
        ) {
            when {
                prayersOverride == null && state.isLoading -> CircularProgressIndicator()
                prayersOverride == null && state.error != null -> Text(text = "Error: ${state.error}")
                prayer != null -> {
                    if (isLandscape) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = dimen24Dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = when {
                                        prayer.prayerName == stringResource(R.string.hail_mary) -> R.drawable.crucifix1
                                        prayer.prayerName == stringResource(R.string.lord_s_prayer) -> R.drawable.cross_of_jesus
                                        prayer.id < ZERO -> R.drawable.easter_cross
                                        else -> R.drawable.celtic_cross
                                    }
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(dimen220Dp)
                                    .padding(end = dimen16Dp)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1.4f)
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = prayer.prayerText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(
                                    id = when {
                                        prayer.prayerName == stringResource(R.string.hail_mary) -> R.drawable.crucifix1
                                        prayer.prayerName == stringResource(R.string.lord_s_prayer) -> R.drawable.cross_of_jesus
                                        prayer.id < ZERO -> R.drawable.easter_cross
                                        else -> R.drawable.celtic_cross
                                    }
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimen120Dp)
                                    .padding(horizontal = dimen24Dp, vertical = dimen8Dp)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState())
                                    .padding(horizontal = dimen24Dp, vertical = dimen16Dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = prayer.prayerText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
                else -> {
                    Image(
                        painter = painterResource(id = R.drawable.celtic_cross),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimen120Dp)
                            .padding(horizontal = dimen24Dp, vertical = dimen8Dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen16Dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentIndex > ZERO && !pageTransitionState.isAnimating) {
                        coroutineScope.launch {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            delay(ONE_HUNDRED.milliseconds)
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            pageTransitionState.animatePageChange(direction = -ONE) {
                                currentIndex -= ONE
                            }
                        }
                    }
                },
                enabled = currentIndex > ZERO && !pageTransitionState.isAnimating,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimen8Dp)
                    .defaultMinSize(minHeight = 48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.previous),
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = {
                    if (!isAudioButtonEnabled) return@Button
                    val currentPlayer = mediaPlayer
                    if (currentPlayer != null) {
                        if (currentPlayer.isPlaying) {
                            currentPlayer.pause()
                            isAudioPlaying = false
                        } else {
                            currentPlayer.start()
                            isAudioPlaying = true
                        }
                    } else {
                        MediaPlayer.create(context, selectedAudioResId).apply {
                            setOnCompletionListener {
                                isAudioPlaying = false
                                it.release()
                                mediaPlayer = null
                            }
                            start()
                        }.also {
                            mediaPlayer = it
                            isAudioPlaying = true
                        }
                    }
                },
                enabled = isAudioButtonEnabled && !pageTransitionState.isAnimating,
                modifier = Modifier
                    .weight(0.9f)
                    .padding(horizontal = dimen8Dp)
                    .defaultMinSize(minHeight = dimen48Dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isAudioPlaying) {
                        stringResource(R.string.pause_audio)
                    } else {
                        stringResource(R.string.play_audio)
                    }
                )
            }
            Button(
                onClick = {
                    if (currentIndex < prayers.size - ONE && !pageTransitionState.isAnimating) {
                        coroutineScope.launch {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            pageTransitionState.animatePageChange(direction = ONE) {
                                currentIndex += ONE
                            }
                        }
                    }
                },
                enabled = currentIndex < prayers.size - ONE && !pageTransitionState.isAnimating,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimen8Dp)
                    .defaultMinSize(minHeight = dimen48Dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.next),
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private class PageTransitionState(private val density: Density) {
    private val pageOffsetX = Animatable(0f)
    private val pageAlpha = Animatable(1f)
    var isAnimating by mutableStateOf(false)
        private set

    suspend fun animatePageChange(direction: Int, onPageSwap: () -> Unit) {
        if (isAnimating) return

        isAnimating = true
        val exitOffset = with(density) {
            if (direction > ZERO) (-dimen72Dp).toPx() else dimen72Dp.toPx()
        }
        coroutineScope {
            launch { pageOffsetX.animateTo(exitOffset, animationSpec = tween(durationMillis = ONE_EIGHTY)) }
            launch { pageAlpha.animateTo(0f, animationSpec = tween(durationMillis = ONE_EIGHTY)) }
        }

        onPageSwap()

        val enterOffset = with(density) {
            if (direction > ZERO) dimen72Dp.toPx() else (-dimen72Dp).toPx()
        }
        pageOffsetX.snapTo(enterOffset)
        pageAlpha.snapTo(0f)
        coroutineScope {
            launch { pageOffsetX.animateTo(0f, animationSpec = tween(durationMillis = TWO_SIXTY)) }
            launch { pageAlpha.animateTo(1f, animationSpec = tween(durationMillis = TWO_SIXTY)) }
        }
        isAnimating = false
    }

    fun Modifier.pageTransitionGraphics(): Modifier = graphicsLayer {
        translationX = pageOffsetX.value
        alpha = pageAlpha.value
    }
}

@Composable
private fun rememberPageTransitionState(): PageTransitionState {
    val density = LocalDensity.current
    return remember(density) { PageTransitionState(density) }
}

private fun Modifier.applyPageTransition(state: PageTransitionState): Modifier {
    return state.run { pageTransitionGraphics() }
}

@Preview(showBackground = true, widthDp = THREE_SIXTY, heightDp = SIX_FORTY)
@Composable
private fun PrayerDetailScreenPreview() {
    MyPrayerTheme {
        PrayerDetailScreen(
            startIndex = 0,
            onNavigateBack = {},
            prayersOverride = listOf(
                RosaryPrayersItem(
                    id = 1,
                    prayerName = "Hail Mary",
                    prayerText = "Hail Mary, full of grace, the Lord is with thee. Blessed art thou amongst women, and blessed is the fruit of thy womb, Jesus.",
                ),
                RosaryPrayersItem(
                    id = 2,
                    prayerName = "Lord's Prayer",
                    prayerText = "Our Father, who art in heaven, hallowed be thy name. Thy kingdom come, thy will be done, on earth as it is in heaven. Give us this day our daily bread, and forgive us our trespasses, as we forgive those who trespass against us. And lead us not into temptation, but deliver us from evil.",
                )
            )
        )
    }
}
