package com.curiousapps.myprayer.repository

import android.content.Context
import android.util.Log
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.RosaryMysteriesResponse
import com.curiousapps.myprayer.data.RosaryMysterySet
import com.curiousapps.myprayer.data.RosaryPrayers
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.getValue

interface RosaryRepository {
    suspend fun getRosaryPrayers(): List<RosaryPrayersItem>
    suspend fun getMysterySetForDay(dayOfWeek: DayOfWeek): RosaryMysterySet?
}

@Singleton
class RosaryRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : RosaryRepository {

    private val rosaryPrayers by lazy { parseRosaryPrayerList() }
    private val rosaryMysterySets by lazy { parseRosaryMysterySets() }

    override suspend fun getRosaryPrayers(): List<RosaryPrayersItem> = rosaryPrayers

    override suspend fun getMysterySetForDay(dayOfWeek: DayOfWeek): RosaryMysterySet? {
        val expectedSetName = when (dayOfWeek) {
            DayOfWeek.MONDAY, DayOfWeek.SATURDAY -> "Joyful Mysteries"
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> "Sorrowful Mysteries"
            DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY -> "Glorious Mysteries"
            DayOfWeek.THURSDAY -> "Luminous Mysteries"
        }
        return rosaryMysterySets.firstOrNull { it.name == expectedSetName }
    }

    private fun parseRosaryPrayerList(): List<RosaryPrayersItem> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.rosary_prayers)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, RosaryPrayers::class.java)
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to parse rosary_prayers.json", exception)
            emptyList()
        }
    }

    private fun parseRosaryMysterySets(): List<RosaryMysterySet> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.rosary_mysteries)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, RosaryMysteriesResponse::class.java).mysterySets
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to parse rosary_mysteries.json", exception)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "RosaryRepository"
    }
}