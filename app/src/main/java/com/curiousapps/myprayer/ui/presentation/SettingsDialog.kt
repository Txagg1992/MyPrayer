package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.AudioVoice
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen12Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen20Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen4Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen8Dp

@Composable
fun SettingsDialog(
    audioEnabled: Boolean,
    selectedVoice: AudioVoice,
    onAudioEnabledChange: (Boolean) -> Unit,
    onVoiceChange: (AudioVoice) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimen20Dp, vertical = dimen16Dp),
                verticalArrangement = Arrangement.spacedBy(dimen12Dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.done))
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(R.string.enable_audio))
                        Switch(
                            checked = audioEnabled,
                            onCheckedChange = onAudioEnabledChange
                        )
                    }
                    Text(
                        text = stringResource(R.string.audio_voice),
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Column(
                        modifier = Modifier.selectableGroup()
                    ) {
                        VoiceRadioOption(
                            title = stringResource(R.string.male_voice),
                            selected = selectedVoice == AudioVoice.MALE,
                            onClick = { onVoiceChange(AudioVoice.MALE) }
                        )
                        VoiceRadioOption(
                            title = stringResource(R.string.female_voice),
                            selected = selectedVoice == AudioVoice.FEMALE,
                            onClick = { onVoiceChange(AudioVoice.FEMALE) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VoiceRadioOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick
            )
            .padding(vertical = dimen8Dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.size(dimen20Dp)
        )
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimen4Dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
