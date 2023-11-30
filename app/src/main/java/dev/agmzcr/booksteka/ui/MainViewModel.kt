package dev.agmzcr.booksteka.ui

import android.util.EventLog
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.agmzcr.booksteka.data.entity.*
import dev.agmzcr.booksteka.data.model.MangasResponse
import dev.agmzcr.booksteka.data.repository.BooksRepository
import dev.agmzcr.booksteka.util.ApiException
import dev.agmzcr.booksteka.util.Event
import dev.agmzcr.booksteka.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BooksRepository,
    state: SavedStateHandle
): ViewModel() {

    private lateinit var mangasResponse: MangasResponse

    val bookState = state.get<BooksComplete>("book")
    val idState = state.get<Int>("bookId")

    private val _collectionLiveData = MutableLiveData<List<MangaEntity>>()
    val collectionLiveData: LiveData<List<MangaEntity>> = _collectionLiveData

    private val _mangasLiveData = MutableLiveData<Event<State<MangasResponse>>?>()
    val mangasLiveData: LiveData<Event<State<MangasResponse>>?>
        get() = _mangasLiveData

    private val _dbData = MutableLiveData<BooksComplete>()
    val dbData: LiveData<BooksComplete>
        get() = _dbData

    private val _readingData = MutableLiveData<List<MangaEntity>>()
    val readingData: LiveData<List<MangaEntity>>
        get() = _readingData

    private val _wishingData = MutableLiveData<List<MangaEntity>>()
    val wishingData: LiveData<List<MangaEntity>>
        get() = _wishingData

    private val _readAllData = MutableLiveData<List<MangaWithGenre>>()
    var readAllData: LiveData<List<MangaWithGenre>> = _readAllData

    private val _readAllData2 = MutableLiveData<List<MangaWithAuthor>>()
    var readAllData2: LiveData<List<MangaWithAuthor>> = _readAllData2

    fun getManga(id: Long) {
        viewModelScope.launch {
            /*_readAllData.postValue(repository.getMangaGenre(id))
            _readAllData2.postValue(repository.getMangaAuthor(id))*/
            Log.i("MANGAS", readingData.toString())
        }
    }

    init {
        //addManga()
        //addBook()
        readingMangas()
        wishMangas()
    }

    fun getWishAndReadingList() {
        wishMangas()
        readingMangas()
    }

    fun addOneMore(id: Long, volume: Int) {
        viewModelScope.launch {
            repository.addOneMore(id, volume)
        }
    }

    fun fetchData() {
        //getBookDetails()
    }

    fun getCollection() {
        viewModelScope.launch {
            _collectionLiveData.value = repository.getAllMangas()
        }
    }

    fun getCollectionBySearch(query: String) {
        viewModelScope.launch {
            _collectionLiveData.value = repository.getAllMangasSearch(query)
        }
    }

    fun deleteManga(id: Long) {
        viewModelScope.launch {
            repository.deleteManga(id)
        }
    }
    /*fun searchByTitle(title: String) {
        viewModelScope.launch {
            _collectionLiveData.value = repository.searchByTitle(title)
        }
    }*/

    /*private fun getBookDetails() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.i("LOGSTATE", bookState.toString())
            Log.i("LOGSTATEID", idState.toString())
            if (bookState != null) {
                _dbData.value = bookState.books.id?.let { repository.getBook(it) }
            } else {
                _dbData.value = repository.getBook(idState!!)
            }
        }
    }*/

    private fun wishMangas() {
        viewModelScope.launch(Dispatchers.Main) {
            _wishingData.value = repository.getWishList()
        }
    }

    private fun wishMangasAgain() {
        viewModelScope.launch(Dispatchers.Main) {
            _wishingData.value = repository.getWishList()
        }
    }

    private fun readingMangas() {
        viewModelScope.launch(Dispatchers.Main) {
            _readingData.value = repository.getReadingList()
        }
    }

    private fun readingMangasAgain() {
        viewModelScope.launch(Dispatchers.Main) {
            _readingData.value = repository.getReadingList()
        }
    }

    fun getMangasList() {
        _mangasLiveData.postValue(Event(State.loading()))
        _mangasLiveData.value = null
        viewModelScope.launch(Dispatchers.Main) {
                try {
                    mangasResponse = repository.getMangasList()
                    Log.i("APITEST", mangasResponse.toString())
                    _mangasLiveData.postValue(Event(State.success(mangasResponse)))
                    Log.i("LIVEDATATEST", _mangasLiveData.toString())
                } catch (e: ApiException) {
                    _mangasLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            }
        }

    fun getMangaListByQuery(query: String) {
            _mangasLiveData.postValue(null)
            _mangasLiveData.postValue(Event(State.loading()))
            //_mangasLiveData.postValue(null)
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    mangasResponse = repository.getMangasByQuery(query)
                    Log.i("APITEST", mangasResponse.toString())
                    _mangasLiveData.postValue(Event(State.success(mangasResponse)))
                    Log.i("LIVEDATATEST", _mangasLiveData.toString())
                } catch (e: ApiException) {
                    _mangasLiveData.postValue(Event(State.error(e.message ?: "")))
                }
        }
    }

    fun updateToRead(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.updateToRead(id)
            readingMangasAgain()
        }
    }

    fun updateToReading(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.updateToReading(id)
            wishMangasAgain()
            readingMangasAgain()
        }
    }

    /*fun addBook() {
        viewModelScope.launch(Dispatchers.Main) {

            book.title = "El Hobbit"
            book.pages = 250
            book.pagesRead = 250
            book.authorId = 1
            book.genreId = 1
            book.publisherId = 1
            book.isRead = true
            book.isReading = false
            book.isWish = false

            author.name = "Jimmy"
            genre.genre = "Ficción"
            publisher.name = "Amazon"

            repository.insertBook(book, author, genre, publisher)
            //repository.insertBook2(book, author, genre)
            //_dbData.value = repository.getBook(5)
            //Log.i("DAOTEST", _dbData.toString())

            book.title = "Manolito Gafota"
            book.pages = 200
            book.pagesRead = 89
            book.authorId = 2
            book.genreId = 2
            book.publisherId = 2
            book.isRead = false
            book.isReading = true
            book.isWish = false

            author.name = "Marta"
            genre.genre = "Humor"
            publisher.name = "Twitch"

            repository.insertBook(book, author, genre, publisher)

            book.title = "Harry Potter"
            book.pages = 470
            book.pagesRead = 0
            book.authorId = 3
            book.genreId = 3
            book.publisherId = 3
            book.isRead = false
            book.isReading = false
            book.isWish = true

            author.name = "Rocio"
            genre.genre = "Terror"
            publisher.name = "London Writer"

            repository.insertBook(book, author, genre, publisher)

            book.title = "Sr Maton"
            book.pages = 140
            book.pagesRead = 0
            book.authorId = 4
            book.genreId = 4
            book.publisherId = 4
            book.isRead = false
            book.isReading = false
            book.isWish = true

            author.name = "J. Perez"
            genre.genre = "Thriller"
            publisher.name = "Kosmos"

            repository.insertBook(book, author, genre, publisher)
        }
    }*/
}