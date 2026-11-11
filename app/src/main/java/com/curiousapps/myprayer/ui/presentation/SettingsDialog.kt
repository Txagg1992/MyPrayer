package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.AudioVoice
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen8Dp

@Composable
fun SettingsDialog(
    audioEnabled: Boolean,
    selectedVoice: AudioVoice,
    onAudioEnabledChange: (Boolean) -> Unit,
    onVoiceChange: (AudioVoice) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.settings)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimen16Dp)
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

                Text(text = stringResource(R.string.audio_voice))

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
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.done))
            }
        }
    )
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
            onClick = null
        )
        Text(text = title)
    }
}
