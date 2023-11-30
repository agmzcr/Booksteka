package dev.agmzcr.booksteka.data.model

data class MangasResults(
    val mal_id: Long,
    val url: String,
    val image_url: String,
    val title: String,
    val publishing: Boolean,
    val synopsis: String,
    val type: String,
    val chapters: Int,
    val score: Double,
    val start_date: String,
    val end_date: String,
    val members: Long
)
