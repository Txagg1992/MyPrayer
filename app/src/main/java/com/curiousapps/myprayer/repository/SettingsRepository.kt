package com.curiousapps.myprayer.repository

import android.content.Context
import com.curiousapps.myprayer.data.AudioVoice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface SettingsRepository {
    fun isAudioEnabled(): Boolean
    fun getAudioVoice(): AudioVoice
    fun setAudioEnabled(isEnabled: Boolean)
    fun setAudioVoice(voice: AudioVoice)
}

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SettingsRepository {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun isAudioEnabled(): Boolean =
        sharedPreferences.getBoolean(KEY_AUDIO_ENABLED, true)

    override fun getAudioVoice(): AudioVoice {
        val rawValue = sharedPreferences.getString(KEY_AUDIO_VOICE, AudioVoice.MALE.name)
        return AudioVoice.entries.firstOrNull { it.name == rawValue } ?: AudioVoice.MALE
    }

    override fun setAudioEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_AUDIO_ENABLED, isEnabled).apply()
    }

    override fun setAudioVoice(voice: AudioVoice) {
        sharedPreferences.edit().putString(KEY_AUDIO_VOICE, voice.name).apply()
    }

    private companion object {
        private const val PREFS_NAME = "my_prayer_settings"
        private const val KEY_AUDIO_ENABLED = "audio_enabled"
        private const val KEY_AUDIO_VOICE = "audio_voice"
    }
}
