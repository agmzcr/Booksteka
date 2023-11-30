package dev.agmzcr.booksteka.ui.details

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.agmzcr.booksteka.data.entity.*
import dev.agmzcr.booksteka.data.model.Manga
import dev.agmzcr.booksteka.data.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: BooksRepository,
    state: SavedStateHandle
): ViewModel() {

    val bookState = state.get<BooksComplete>("book")
    val idState = state.get<Long>("mangaId")
    private val isFetchFromRemote = state.get<Boolean>("isFetchFromRemote")

    private val _result = MutableLiveData<Manga>()
    val result: LiveData<Manga> = _result

    private val _resultGenre = MutableLiveData<MangaWithGenre>()
    val resultGenre: LiveData<MangaWithGenre> = _resultGenre

    fun fetchData() {
        if (isFetchFromRemote == true) {
            getMangaDetailsFromRemote()
        } else {
            getMangaDetailsFromLocal()
            Log.i("GETREMOTE", "Get data from database")
            Log.i("MANGAID", idState.toString())
        }
    }

    private fun getMangaDetailsFromLocal() {
        viewModelScope.launch(Dispatchers.Main) {
            val manga = Manga(idState!!)
            if (idState != 0L) {
                val mangaGenre = repository.getMangaGenre(idState)
                val mangaAuthor = repository.getMangaAuthor(idState)
                val mangaEntity = repository.getMangaFromDb(idState)
                manga.title = mangaEntity.title
                manga.image_url = mangaEntity.image_url
                manga.synopsis = mangaEntity.synopsis
                manga.score = mangaEntity.score
                manga.volumes = mangaEntity.volumes
                manga.genres = mangaGenre.genre
                manga.authors = mangaAuthor.author
                Log.i("DBTITLE", manga.title!!)
                _result.postValue(manga)
                } else {

                }
            }
        }

    fun getMangaGenres(id: Long) {
        viewModelScope.launch {
            _resultGenre.value = repository.getMangaGenre(id)
        }
    }

    private fun getMangaDetailsFromRemote() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.i("LOGSTATE", bookState.toString())
            Log.i("LOGSTATEID", idState.toString())
            if (bookState != null) {
                //_dbData.value = bookState.books.id?.let { repository.getBook(it) }
            } else {
                _result.value = repository.getMangaFromRemote(idState!!)
            }
        }
    }


    fun isMangaSaved(id: Long): Boolean {
        return repository.isMangaSaved(id)
    }

    fun deleteManga(id: Long) {
        viewModelScope.launch {
            repository.deleteManga(id)
        }
    }

    fun insertMangaOnDb() {
        viewModelScope.launch(Dispatchers.Main) {
            val manga = MangaEntity()
            manga.manga_Id = result.value?.manga_Id!!
            manga.title = result.value?.title
            manga.image_url = result.value?.image_url
            manga.synopsis = result.value?.synopsis
            manga.volumes = result.value?.volumes
            manga.volumesRead = 0
            manga.score = result.value?.score
            manga.isRead = false
            manga.isReading = false
            manga.isWish = true
            repository.insertManga(manga)

            val genre = GenreEntity()
            result.value?.genres?.forEach {
                //genre.mangaId = result.value!!.manga_Id!!
                genre.genre_Id = it.genre_Id
                genre.name = it.name
                repository.insertGenre(genre)

                val mangaGenre = MangaGenreCrossRef(result.value!!.manga_Id!!, it.genre_Id!!)
                repository.insertMangaGenre(mangaGenre)
            }

            val author = AuthorsEntity()
            result.value?.authors?.forEach {
                //author.mangaId = result.value!!.manga_Id!!
                author.author_Id = it.author_Id
                author.name = it.name
                repository.insertAuthor(author)

                val mangaAuthor = MangaAuthorCrossRef(result.value!!.manga_Id!!, it.author_Id!!)
                repository.insertMangaAuthor(mangaAuthor)
            }
        }
    }
}