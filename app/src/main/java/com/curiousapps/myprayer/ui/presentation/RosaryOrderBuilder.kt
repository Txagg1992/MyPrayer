package com.curiousapps.myprayer.ui.presentation

import com.curiousapps.myprayer.data.RosaryMysterySet
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.curiousapps.myprayer.data.mapper.toOrdinal
import com.curiousapps.myprayer.data.mapper.toSingularMysterySetName
import javax.inject.Inject

class RosaryOrderBuilder @Inject constructor() {

    fun build(
        prayerList: List<RosaryPrayersItem>,
        mysterySet: RosaryMysterySet?
    ): List<RosaryPrayersItem> {
        val byName = prayerList.associateBy { it.prayerName }
        val display = mutableListOf<RosaryPrayersItem>()

        fun add(name: String, count: Int = 1) {
            val prayer = byName[name] ?: return
            repeat(count) { display.add(prayer) }
        }

        add("Sign of the Cross")
        add("Apostle's Creed")
        add("Lord's Prayer")
        add("Hail Mary", count = 3)
        add("Glory Be")

        repeat(5) { decadeIndex ->
            val mystery = mysterySet?.mysteries?.getOrNull(decadeIndex)
            if (mystery != null) {
                display.add(
                    RosaryPrayersItem(
                        id = -(decadeIndex + 1),
                        prayerName = buildMysteryAnnouncementName(
                            decadeNumber = decadeIndex + 1,
                            mysterySetName = mysterySet.name,
                            mysteryName = mystery.name
                        ),
                        prayerText = "${mystery.scripture}\n${mystery.meditation}",
                        youtubeUrl = mystery.youtubeUrl.orEmpty()
                    )
                )
            }

            add("Lord's Prayer")
            add("Hail Mary", count = 10)
            add("Glory Be")
            add("Fatima Prayer")
        }

        add("Hail Holy Queen")
        add("Concluding Prayer")

        return display
    }

    private fun buildMysteryAnnouncementName(
        decadeNumber: Int,
        mysterySetName: String,
        mysteryName: String
    ): String {
        val ordinal = decadeNumber.toOrdinal()
        val setDisplayName = mysterySetName.toSingularMysterySetName()
        return "$ordinal $setDisplayName:\n$mysteryName"
    }


}
