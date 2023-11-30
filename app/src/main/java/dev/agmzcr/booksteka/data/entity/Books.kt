package dev.agmzcr.booksteka.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Books(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String? = null,
    var pages: Int? = null,
    //var thumbnail: String? = null,
    var pagesRead: Int? = null,
    var authorId: Int? = null,
    var publisherId: Int? = null,
    var genreId: Int? = null,
    var isReading: Boolean? = null,
    var isRead: Boolean? = null,
    var isWish: Boolean? = null
)
