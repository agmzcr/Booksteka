package dev.agmzcr.booksteka.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publishers")
data class Publishers(
    @PrimaryKey(autoGenerate = true)
    val publishersId: Int? = null,
    var name: String? = null
)
