package com.curiousapps.myprayer.data.mapper

fun Int.toOrdinal(): String {
    val suffix = if (this % 100 in 11..13) {
        "th"
    } else {
        when (this % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
    return "$this$suffix"
}

fun String.toSingularMysterySetName(): String {
    return if (endsWith(" Mysteries")) {
        removeSuffix(" Mysteries") + " Mystery"
    } else {
        this
    }
}