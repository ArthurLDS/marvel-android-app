package br.com.cwi.marvelapp.presentation.feature.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cwi.marvelapp.domain.model.CharacterData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel

private const val TOTAL_ITEMS_PER_PAGE = 10
private const val FIRST_PAGE = 0
private const val MIN_SIZE_SEARCH = 4

class CharactersViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    private val _characters: MutableLiveData<List<CharacterItem>> = MutableLiveData()
    val characters: LiveData<List<CharacterItem>> = _characters

    private val _emptyResult: MutableLiveData<Boolean> = MutableLiveData()
    val emptyResult: LiveData<Boolean> = _emptyResult

    private val _isGridList: MutableLiveData<Boolean> = MutableLiveData(true)
    val isGridList: MutableLiveData<Boolean> = _isGridList

    private var currentPage = 0
    private var termSearch: String? = null

    fun fetchCharacters() {
        launch {
            val response: CharacterData =
                repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, termSearch)

            _characters.postValue(response.results)
        }
    }

    fun fetchCharactersWithPagination() {
        launch(true) {
            currentPage += TOTAL_ITEMS_PER_PAGE

            val response: CharacterData =
                repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, termSearch)

            val shouldLoadMoreItems = currentPage / TOTAL_ITEMS_PER_PAGE < response.total

            if (shouldLoadMoreItems) {
                _characters.value?.let {
                    _characters.postValue(it + response.results)
                } ?: _characters.postValue(response.results)
            }
        }
    }

    fun searchCharacters(term: String) {
        if (term.length >= MIN_SIZE_SEARCH || term.isEmpty()) {
            termSearch = term.ifEmpty { null }
            currentPage = FIRST_PAGE
            launch(true) {
                val response: CharacterData =
                    repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, termSearch)

                if (response.results.isNullOrEmpty()) {
                    _emptyResult.postValue(true)
                } else {
                    _characters.postValue(response.results)
                }
            }
        }
    }

    fun refreshCharacters() {
        launch {
            currentPage = FIRST_PAGE
            termSearch = null
            val response: CharacterData =
                repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, null)
            _characters.postValue(response.results)
        }
    }

    fun setFavorite(character: CharacterItem) = with (character){
        isFavorite = isFavorite.not()
        if (isFavorite)
            repository.add(character)
        else
            repository.delete(character)
    }

    fun setCharactersTypeList(isGrid: Boolean) {
        if (_isGridList.value != isGrid)
            _isGridList.postValue(isGrid)
    }

}