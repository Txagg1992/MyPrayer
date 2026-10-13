package com.curiousapps.myprayer.data.mapper

private const val ORDINAL_TEENS_START = 11
private const val ORDINAL_TEENS_END = 13
private const val ORDINAL_FIRST = 1
private const val ORDINAL_SECOND = 2
private const val ORDINAL_THIRD = 3
private const val DEFAULT_ORDINAL_SUFFIX = "th"
private const val FIRST_ORDINAL_SUFFIX = "st"
private const val SECOND_ORDINAL_SUFFIX = "nd"
private const val THIRD_ORDINAL_SUFFIX = "rd"
private const val MYSTERIES_SUFFIX = " Mysteries"
private const val MYSTERY_SUFFIX = " Mystery"

fun Int.toOrdinal(): String {
    val suffix = if (this % 100 in ORDINAL_TEENS_START..ORDINAL_TEENS_END) {
        DEFAULT_ORDINAL_SUFFIX
    } else {
        when (this % 10) {
            ORDINAL_FIRST -> FIRST_ORDINAL_SUFFIX
            ORDINAL_SECOND -> SECOND_ORDINAL_SUFFIX
            ORDINAL_THIRD -> THIRD_ORDINAL_SUFFIX
            else -> DEFAULT_ORDINAL_SUFFIX
        }
    }
    return "$this$suffix"
}

fun String.toSingularMysterySetName(): String {
    return if (endsWith(MYSTERIES_SUFFIX)) {
        removeSuffix(MYSTERIES_SUFFIX) + MYSTERY_SUFFIX
    } else {
        this
    }
}