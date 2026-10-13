package com.curiousapps.myprayer.ui.presentation

import android.content.Context
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.data.RosaryMysterySet
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.curiousapps.myprayer.data.mapper.toOrdinal
import com.curiousapps.myprayer.data.mapper.toSingularMysterySetName
import com.curiousapps.myprayer.ui.theme.AppDimens.FIVE
import com.curiousapps.myprayer.ui.theme.AppDimens.ONE
import com.curiousapps.myprayer.ui.theme.AppDimens.TEN
import com.curiousapps.myprayer.ui.theme.AppDimens.THREE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RosaryOrderBuilder @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    fun build(
        prayerList: List<RosaryPrayersItem>,
        mysterySet: RosaryMysterySet?
    ): List<RosaryPrayersItem> {
        val byName = prayerList.associateBy { it.prayerName }
        val display = mutableListOf<RosaryPrayersItem>()

        fun add(name: String, count: Int = ONE) {
            val prayer = byName[name] ?: return
            repeat(count) { display.add(prayer) }
        }

        add(context.getString(R.string.sign_of_the_cross))
        add(context.getString(R.string.apostle_s_creed))
        add(context.getString(R.string.lord_s_prayer))
        add(context.getString(R.string.hail_mary), count = THREE)
        add(context.getString(R.string.glory_be))

        repeat(FIVE) { decadeIndex ->
            val mystery = mysterySet?.mysteries?.getOrNull(decadeIndex)
            if (mystery != null) {
                display.add(
                    RosaryPrayersItem(
                        id = -(decadeIndex + 1),
                        prayerName = buildMysteryAnnouncementName(
                            decadeNumber = decadeIndex + ONE,
                            mysterySetName = mysterySet.name,
                            mysteryName = mystery.name
                        ),
                        prayerText = "${mystery.scripture} ${mystery.meditation}",
                        youtubeUrl = mystery.youtubeUrl.orEmpty()
                    )
                )
            }

            add(context.getString(R.string.lord_s_prayer))
            add(context.getString(R.string.hail_mary), count = TEN)
            add(context.getString(R.string.glory_be))
            add(context.getString(R.string.fatima_prayer))
        }

        add(context.getString(R.string.hail_holy_queen))
        add(context.getString(R.string.concluding_prayer))

        return display
    }

    private fun buildMysteryAnnouncementName(
        decadeNumber: Int,
        mysterySetName: String,
        mysteryName: String
    ): String {
        val ordinal = decadeNumber.toOrdinal()
        val setDisplayName = mysterySetName.toSingularMysterySetName()
        return "$ordinal $setDisplayName: $mysteryName"
    }


}
