package dev.agmzcr.booksteka.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BooksAndPublishers(
    @Embedded var books: Books,
    @Relation(
        parentColumn = "publisherId",
        entityColumn = "publishersId"
    )
    var publishers: Publishers
)
