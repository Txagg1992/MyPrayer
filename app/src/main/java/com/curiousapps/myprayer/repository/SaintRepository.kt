package com.curiousapps.myprayer.repository

import android.content.Context
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.SaintPrayers
import com.curiousapps.myprayer.data.SaintPrayersItem
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface SaintRepository {
    suspend fun getSaints(): List<SaintPrayersItem>
}

@Singleton
class SaintRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SaintRepository {

    private val saintPrayers by lazy { parseSaintPrayers() }

    override suspend fun getSaints(): List<SaintPrayersItem> = saintPrayers

    private fun parseSaintPrayers(): List<SaintPrayersItem> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.saint_prayers)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, SaintPrayers::class.java)
        } catch (exception: Exception) {
            Timber.tag(TAG).e(exception, "Failed to parse saint_prayers.json")
            emptyList()
        }
    }

    companion object {
        private const val TAG = "SaintRepository"
    }
}