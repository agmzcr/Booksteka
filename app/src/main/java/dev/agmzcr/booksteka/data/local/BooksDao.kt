package dev.agmzcr.booksteka.data.local

import androidx.room.*
import dev.agmzcr.booksteka.data.entity.*
import dev.agmzcr.booksteka.data.model.Manga

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: MangaEntity)

    @Query("DELETE FROM mangas WHERE manga_Id LIKE :id")
    suspend fun deleteManga(id: Long)

    @Query("SELECT EXISTS(SELECT * FROM mangas WHERE manga_Id LIKE :id)")
    fun isMangaSaved(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: AuthorsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaGenre(item: MangaGenreCrossRef)

    @Query("DELETE FROM mangagenrecrossref WHERE manga_Id LIKE :id")
    suspend fun deleteMangaGenre(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaAuthor(item: MangaAuthorCrossRef)

    @Query("DELETE FROM mangaauthorcrossref WHERE manga_Id LIKE :id")
    suspend fun deleteMangaAuthor(id: Long)

    @Transaction
    @Query("SELECT * FROM mangas")
    suspend fun getAllMangas(): List<MangaEntity>

    @Transaction
    @Query("SELECT * FROM mangas WHERE title LIKE '%' || :title || '%' OR LOWER(title) LIKE '%' || LOWER(:title) || '%' ")
    suspend fun getAllMangasSearch(title: String): List<MangaEntity>

    @Transaction
    @Query("SELECT * FROM mangas WHERE manga_Id = :id")
    suspend fun getManga(id: Long): MangaEntity

    @Transaction
    @Query("SELECT * FROM mangas WHERE isWish = 1")
    suspend fun getWishList(): List<MangaEntity>

    @Transaction
    @Query("SELECT * FROM mangas WHERE isReading = 1")
    suspend fun getReadingList(): List<MangaEntity>

    @Query("UPDATE mangas SET isRead = 1, isWish = 0, isReading = 0 WHERE manga_Id = :id")
    suspend fun updateToRead(id: Long)

    @Query("UPDATE mangas SET isRead = 0, isWish = 0, isReading = 1 WHERE manga_Id = :id")
    suspend fun updateToReading(id: Long)

    @Query("UPDATE mangas SET volumesRead = :volume WHERE manga_Id =:id")
    suspend fun addOneMore(id: Long, volume: Int)

    @Transaction
    @Query("SELECT * FROM mangas WHERE manga_Id = :id")
    suspend fun getMangaWithGenre(id: Long): MangaWithGenre

    @Transaction
    @Query("SELECT * FROM mangas WHERE manga_Id = :id")
    suspend fun getMangaWithAuthor(id: Long): MangaWithAuthor

    /*@Transaction
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BooksComplete>

    @Transaction
    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%' OR LOWER(title) LIKE '%' || LOWER(:title) || '%' ")
    suspend fun searchByTitle(title: String): List<BooksComplete>

    @Transaction
    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%' OR LOWER(title) LIKE '%' || LOWER(:title) || '%' ")
    suspend fun searchByTitle2(title: String): List<BooksComplete>

    @Transaction
    @Query("SELECT * FROM books WHERE id IS :id")
    suspend fun getBook(id: Int): BooksComplete

    @Transaction
    @Query("SELECT * FROM books WHERE isReading IS :isReading")
    suspend fun getReadingBooks(isReading: Int): List<BooksComplete>

    @Transaction
    @Query("SELECT * FROM books WHERE isWish IS :isWish")
    suspend fun getWishBooks(isWish: Int): List<BooksComplete>

    @Query("UPDATE books SET isRead = 1, isWish = 0, isReading = 0 WHERE id = :id")
    suspend fun updateToRead(id: Int)

    @Query("UPDATE books SET isRead = 0, isWish = 0, isReading = 1 WHERE id = :id")
    suspend fun updateToReading(id: Int)*/
}