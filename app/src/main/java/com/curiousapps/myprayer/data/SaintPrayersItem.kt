package com.curiousapps.myprayer.data

data class SaintPrayersItem(
    val id: Int,
    val prayerName: String,
    val prayerText: String,
    val audioFile: PrayerAudio = PrayerAudio()
)