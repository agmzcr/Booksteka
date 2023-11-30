package dev.agmzcr.booksteka.data.repository

import dev.agmzcr.booksteka.data.entity.*
import dev.agmzcr.booksteka.data.local.BooksDatabase
import dev.agmzcr.booksteka.data.model.Manga
import dev.agmzcr.booksteka.data.model.MangasResponse
import dev.agmzcr.booksteka.data.remote.BooksService
import dev.agmzcr.booksteka.data.remote.SafeApiRequest

class BooksRepository(
    private val api: BooksService,
    private val db: BooksDatabase
): SafeApiRequest() {

    // Remote

    suspend fun getMangasList(): MangasResponse = apiRequest {
        api.searchMangas()
    }

    suspend fun getMangaFromRemote(id: Long): Manga = apiRequest {
        api.getManga(id)
    }

    suspend fun getMangasByQuery(query: String): MangasResponse = apiRequest {
        api.searchMangasByQuery(query)
    }

    // Room

    suspend fun insertManga(mangas: MangaEntity) {
        db.booksDao().insertManga(mangas)
    }

    fun isMangaSaved(id: Long): Boolean {
        return db.booksDao().isMangaSaved(id)
    }

    suspend fun deleteManga(id: Long) {
        db.booksDao().deleteManga(id)
        db.booksDao().deleteMangaGenre(id)
        db.booksDao().deleteMangaAuthor(id)
    }

    suspend fun insertGenre(genre: GenreEntity) {
        db.booksDao().insertGenre(genre)
    }

    suspend fun insertAuthor(author: AuthorsEntity) {
        db.booksDao().insertAuthor(author)
    }

    suspend fun insertMangaGenre(item: MangaGenreCrossRef) {
        db.booksDao().insertMangaGenre(item)
    }

    suspend fun insertMangaAuthor(item: MangaAuthorCrossRef) {
        db.booksDao().insertMangaAuthor(item)
    }

    suspend fun getAllMangas(): List<MangaEntity> {
        return db.booksDao().getAllMangas()
    }

    suspend fun getAllMangasSearch(query: String): List<MangaEntity> {
        return db.booksDao().getAllMangasSearch(query)
    }

    suspend fun getWishList(): List<MangaEntity> {
        return db.booksDao().getWishList()
    }

    suspend fun getReadingList(): List<MangaEntity> {
        return db.booksDao().getReadingList()
    }

    suspend fun updateToRead(id: Long) {
        db.booksDao().updateToRead(id)
    }

    suspend fun updateToReading(id: Long) {
        db.booksDao().updateToReading(id)
    }

    suspend fun addOneMore(id: Long, volume: Int) {
        db.booksDao().addOneMore(id, volume)
    }

    suspend fun getMangaFromDb(id: Long): MangaEntity {
        return db.booksDao().getManga(id)
    }

    suspend fun getMangaGenre(id: Long): MangaWithGenre {
        return db.booksDao().getMangaWithGenre(id)
    }

    suspend fun getMangaAuthor(id: Long): MangaWithAuthor {
        return db.booksDao().getMangaWithAuthor(id)
    }

    /*suspend fun getData(): List<BooksComplete> {
        return db.booksDao().getAllBooks()
    }

    suspend fun searchByTitle(title: String): List<BooksComplete> {
        return db.booksDao().searchByTitle(title)
    }

    suspend fun getBook(id: Int): BooksComplete {
        return db.booksDao().getBook(id)
    }

    suspend fun getReadingBooks(isReading: Int): List<BooksComplete> {
        return db.booksDao().getReadingBooks(isReading)
    }

    suspend fun getWishBooks(isWish: Int): List<BooksComplete> {
        return db.booksDao().getWishBooks(isWish)
    }

    suspend fun updateToReading(id: Int) {
        db.booksDao().updateToReading(id)
    }*/
}