package dev.agmzcr.booksteka.data.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import dev.agmzcr.booksteka.data.model.MangaType

@Entity(tableName = "mangas")
data class MangaEntity(
    @PrimaryKey
    @SerializedName("mal_id")
    var manga_Id: Long? = null,
    var title: String? = null,
    var image_url: String? = null,
    //val authors: List<String>? = null,
    //val genres: List<String>? = null,
    var synopsis: String? = null,
    var volumes: Int? = null,
    var volumesRead: Int? = null,
    var score: Double? = null,
    var isReading: Boolean? = null,
    var isRead: Boolean? = null,
    var isWish: Boolean? = null,
    var addedDate: String? = null,
    var updateDate: String? = null
)

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    @SerializedName("mal_id")
    var genre_Id: Long? = null,
    var name: String? = null,
    //var mangaId: Long? = null
)

@Entity(primaryKeys = ["manga_Id", "genre_Id"])
data class MangaGenreCrossRef(
    val manga_Id: Long,
    val genre_Id: Long
)

data class MangaWithGenre(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "manga_Id",
        entityColumn = "genre_Id",
        associateBy = Junction(MangaGenreCrossRef::class)
    )
    val genre: List<GenreEntity>
)

data class GenreWithManga(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "genre_Id",
        entityColumn = "manga_Id",
        associateBy = Junction(MangaGenreCrossRef::class)
    )
    val manga: List<MangaEntity>
)

@Entity(tableName = "authors")
data class AuthorsEntity(
    @PrimaryKey
    @SerializedName("mal_id")
    var author_Id: Long? = null,
    var name: String? = null,
    //var mangaId: Long? = null
)

@Entity(primaryKeys = ["manga_Id", "author_Id"])
data class MangaAuthorCrossRef(
    val manga_Id: Long,
    val author_Id: Long
)

data class MangaWithAuthor(
    @Embedded val manga: MangaEntity,
    @Relation(
        parentColumn = "manga_Id",
        entityColumn = "author_Id",
        associateBy = Junction(MangaAuthorCrossRef::class)
    )
    val author: List<AuthorsEntity>
)

data class AuthorWithManga(
    @Embedded val author: AuthorsEntity,
    @Relation(
        parentColumn = "author_Id",
        entityColumn = "manga_Id",
        associateBy = Junction(MangaAuthorCrossRef::class)
    )
    val manga: List<MangaEntity>
)