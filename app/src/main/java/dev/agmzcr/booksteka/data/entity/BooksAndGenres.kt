package dev.agmzcr.booksteka.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BooksAndGenres(
    @Embedded var books: Books,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "genresId"
    )
    var genres: Genres
)
