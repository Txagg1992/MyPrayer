package com.curiousapps.myprayer.data

data class RosaryPrayersItem(
    val id: Int,
    val prayerName: String,
    val prayerText: String,
    val audio: PrayerAudio = PrayerAudio()
)

data class PrayerAudio(
    val male: String = "",
    val female: String = ""
)