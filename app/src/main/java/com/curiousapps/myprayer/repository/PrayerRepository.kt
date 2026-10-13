package com.curiousapps.myprayer.repository

import android.content.Context
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.RosaryMysteriesResponse
import com.curiousapps.myprayer.data.RosaryMysterySet
import com.curiousapps.myprayer.data.RosaryPrayers
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

interface PrayerRepository {
    suspend fun getRosaryPrayers(): List<RosaryPrayersItem>
    suspend fun getMysterySetForDay(dayOfWeek: DayOfWeek): RosaryMysterySet?
}

@Singleton
class PrayerRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : PrayerRepository {

    private val rosaryPrayers by lazy { parseRosaryPrayerList() }
    private val rosaryMysterySets by lazy { parseRosaryMysterySets() }

    override suspend fun getRosaryPrayers(): List<RosaryPrayersItem> = rosaryPrayers

    override suspend fun getMysterySetForDay(dayOfWeek: DayOfWeek): RosaryMysterySet? {
        val expectedSetName = when (dayOfWeek) {
            DayOfWeek.MONDAY, DayOfWeek.SATURDAY -> context.getString(R.string.joyful_mysteries)
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> context.getString(R.string.sorrowful_mysteries)
            DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY -> context.getString(R.string.glorious_mysteries)
            DayOfWeek.THURSDAY -> context.getString(R.string.luminous_mysteries)
        }
        return rosaryMysterySets.firstOrNull { it.name == expectedSetName }
    }

    private fun parseRosaryPrayerList(): List<RosaryPrayersItem> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.rosary_prayers)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, RosaryPrayers::class.java)
        } catch (exception: Exception) {
            Timber.tag(TAG).e(exception,
                context.getString(R.string.failed_to_parse_rosary_prayers_json))
            emptyList()
        }
    }

    private fun parseRosaryMysterySets(): List<RosaryMysterySet> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.rosary_mysteries)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, RosaryMysteriesResponse::class.java).mysterySets
        } catch (exception: Exception) {
            Timber.tag(TAG).e(exception,
                context.getString(R.string.failed_to_parse_rosary_mysteries_json))
            emptyList()
        }
    }

    companion object {
        private const val TAG = "RosaryRepository"
    }
}