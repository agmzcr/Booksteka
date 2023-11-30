package dev.agmzcr.booksteka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.agmzcr.booksteka.data.entity.*

@Database(entities = [MangaEntity::class, GenreEntity::class, MangaGenreCrossRef::class, AuthorsEntity::class, MangaAuthorCrossRef::class], version = 23, exportSchema = true)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao
}