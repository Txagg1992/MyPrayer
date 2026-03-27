package com.curiousapps.myprayer.data

data class RosaryMysteriesResponse(
    val mysterySets: List<RosaryMysterySet>
)

data class RosaryMysterySet(
    val id: Int,
    val name: String,
    val day: String,
    val mysteries: List<RosaryMystery>
)

data class RosaryMystery(
    val id: Int,
    val name: String,
    val scripture: String,
    val youtubeUrl: String?,
    val meditation: String
)

