package dev.agmzcr.booksteka.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genres(
    @PrimaryKey(autoGenerate = true)
    val genresId: Int? = null,
    var genre: String? = null,
)
