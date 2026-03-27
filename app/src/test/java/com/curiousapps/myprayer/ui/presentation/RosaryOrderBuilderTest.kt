package com.curiousapps.myprayer.ui.presentation

import com.curiousapps.myprayer.data.RosaryMystery
import com.curiousapps.myprayer.data.RosaryMysterySet
import com.curiousapps.myprayer.data.RosaryPrayersItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RosaryOrderBuilderTest {

    private val builder = RosaryOrderBuilder()

    @Test
    fun openingSequence_isCorrect() {
        val order = builder.build(basePrayerList(), mysterySet())

        val opening = order.take(7).map { it.prayerName }
        assertEquals(
            listOf(
                "Sign of the Cross",
                "Apostle's Creed",
                "Lord's Prayer",
                "Hail Mary",
                "Hail Mary",
                "Hail Mary",
                "Glory Be"
            ),
            opening
        )
    }

    @Test
    fun mysteryAnnouncement_usesOrdinalAndSetName() {
        val order = builder.build(basePrayerList(), mysterySet())

        assertTrue(order.any { it.prayerName == "1st Joyful Mystery: Mystery 1" })
        assertTrue(order.any { it.prayerName == "2nd Joyful Mystery: Mystery 2" })
    }

    @Test
    fun mysteryComesImmediatelyBeforeEachDecadeLordsPrayer() {
        val order = builder.build(basePrayerList(), mysterySet())

        val ordinals = listOf("1st", "2nd", "3rd", "4th", "5th")
        repeat(5) { decadeIndex ->
            val mysteryName = "${ordinals[decadeIndex]} Joyful Mystery: Mystery ${decadeIndex + 1}"
            val mysteryIndex = order.indexOfFirst { it.prayerName == mysteryName }

            assertTrue("Missing $mysteryName", mysteryIndex >= 0)
            assertEquals("Lord's Prayer", order[mysteryIndex + 1].prayerName)
        }
    }

    @Test
    fun prayerCounts_areCorrect() {
        val order = builder.build(basePrayerList(), mysterySet())

        assertEquals(6, order.count { it.prayerName == "Lord's Prayer" })
        assertEquals(53, order.count { it.prayerName == "Hail Mary" })
        assertEquals(6, order.count { it.prayerName == "Glory Be" })
        assertEquals(5, order.count { it.prayerName == "Fatima Prayer" })
        assertEquals(5, order.count { it.prayerName.contains(" Joyful Mystery: ") })
    }

    private fun basePrayerList(): List<RosaryPrayersItem> {
        return listOf(
            prayer(1, "Sign of the Cross"),
            prayer(2, "Apostle's Creed"),
            prayer(3, "Lord's Prayer"),
            prayer(4, "Hail Mary"),
            prayer(5, "Glory Be"),
            prayer(6, "Fatima Prayer"),
            prayer(7, "Hail Holy Queen"),
            prayer(8, "Concluding Prayer")
        )
    }

    private fun mysterySet(): RosaryMysterySet {
        return RosaryMysterySet(
            id = 1,
            name = "Joyful Mysteries",
            day = "Monday, Saturday",
            mysteries = (1..5).map { index ->
                RosaryMystery(
                    id = index,
                    name = "Mystery $index",
                    scripture = "Scripture $index",
                    youtubeUrl = null,
                    meditation = "Meditation $index"
                )
            }
        )
    }

    private fun prayer(id: Int, name: String): RosaryPrayersItem {
        return RosaryPrayersItem(
            id = id,
            prayerName = name,
            prayerText = "$name text",
            youtubeUrl = ""
        )
    }
}
