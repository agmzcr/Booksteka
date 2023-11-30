package dev.agmzcr.booksteka.data.remote

import dev.agmzcr.booksteka.data.model.Manga
import dev.agmzcr.booksteka.data.model.MangasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksService {
    @GET("search/manga")
    suspend fun searchMangas(@Query("q") searchQuery: String = "",
                             @Query("order_by") orderBy: String = "members",
                             @Query("sort") sort: String = "desc",
                             @Query("page") page: Int = 1
    ): Response<MangasResponse>

    @GET("manga/{id}")
    suspend fun getManga(@Path("id") id: Long
    ): Response<Manga>

    @GET("search/manga")
    suspend fun searchMangasByQuery(@Query("q") searchQuery: String,
                             @Query("order_by") orderBy: String = "members",
                             @Query("sort") sort: String = "desc",
                             @Query("page") page: Int = 1
    ): Response<MangasResponse>
}