package dev.agmzcr.booksteka.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dev.agmzcr.booksteka.data.entity.AuthorsEntity
import dev.agmzcr.booksteka.data.entity.GenreEntity

data class Manga(
    @SerializedName("mal_id")
    var manga_Id: Long? = null,
    var title: String? = null,
    var image_url: String? = null,
    var authors: List<AuthorsEntity>? = null,
    var genres: List<GenreEntity>? = null,
    var synopsis: String? = null,
    var volumes: Int? = null,
    var volumesRead: Int? = null,
    var score: Double? = null,
    var isReading: Boolean? = null,
    var isRead: Boolean? = null,
    var isWish: Boolean? = null

    /*val mal_id: Long,
    var title: String? = null,
    var image_url: String? = null,
    val authors: List<GenreEntity>? = null,
    val genres: List<GenreEntity>? = null,
    var synopsis: String? = null,
    val volumes: Int? = null,
    var score: Double? = null*/
)

data class MangaType(
    val mal_id: Long? = null,
    val type: String? = null,
    val name: String? = null,
    val url: String? = null
)