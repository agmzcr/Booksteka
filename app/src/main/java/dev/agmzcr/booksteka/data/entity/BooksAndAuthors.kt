package dev.agmzcr.booksteka.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BooksAndAuthors(
    @Embedded var books: Books,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "authorsId"
    )
    var authors: Authors
)
