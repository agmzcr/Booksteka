package dev.agmzcr.booksteka.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Authors(
    @PrimaryKey(autoGenerate = true)
    val authorsId: Int? = null,
    var name: String? = null
)
