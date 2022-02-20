package br.com.cwi.marvelapp.presentation.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.data.model.CharacterDataResponse
import br.com.cwi.marvelapp.domain.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel

private const val TOTAL_ITEMS_PER_PAGE = 20
private const val FIRST_PAGE = 0
private const val MIN_SIZE_SEARCH = 4

class CharactersViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    private val _charactersLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    val charactersLiveData: LiveData<List<Character>> = _charactersLiveData

    private var currentPage = 0

    fun fetchCharacters(nextPage : Boolean = false) {
        launch(true) {

            val response: CharacterDataResponse =
                repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage)

            val shouldLoadMoreItems = currentPage / TOTAL_ITEMS_PER_PAGE < response.total

            if (nextPage) currentPage += TOTAL_ITEMS_PER_PAGE

            if (shouldLoadMoreItems) {
                _charactersLiveData.value?.let {
                    _charactersLiveData.postValue(it + response.results)
                } ?: _charactersLiveData.postValue(response.results)
            }
        }
    }

    fun searchCharacters(term: String) {
        if (term.length >= MIN_SIZE_SEARCH) {
            currentPage = FIRST_PAGE
            launch(true) {
                val response: CharacterDataResponse =
                    repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, term)

                _charactersLiveData.postValue(response.results)
            }
        }
    }

    fun refreshCharacters() {
        launch {
            currentPage = FIRST_PAGE
            val response: CharacterDataResponse =
                repository.getCharacters(TOTAL_ITEMS_PER_PAGE, currentPage, null)
            _charactersLiveData.postValue(response.results)
        }
    }

}