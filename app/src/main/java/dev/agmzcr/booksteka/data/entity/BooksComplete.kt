package dev.agmzcr.booksteka.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BooksComplete(
    @Embedded
    var books: @RawValue Books,

    @Relation(
        parentColumn = "publisherId",
        entityColumn = "publishersId"
    )
    var publishers: @RawValue Publishers,

    @Relation(
        parentColumn = "authorId",
        entityColumn = "authorsId"
    )
    var authors: @RawValue Authors,

    @Relation(
        parentColumn = "genreId",
        entityColumn = "genresId"
    )
    var genres: @RawValue Genres
): Parcelable
