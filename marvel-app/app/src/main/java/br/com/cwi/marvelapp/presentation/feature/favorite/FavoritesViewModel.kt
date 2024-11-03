package br.com.cwi.marvelapp.presentation.feature.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel
import kotlinx.coroutines.delay

class FavoritesViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    private val _favorites: MutableLiveData<List<CharacterItem>> = MutableLiveData()
    val favorites: LiveData<List<CharacterItem>> = _favorites

    private val _emptyResult: MutableLiveData<Boolean> = MutableLiveData()
    val emptyResult: LiveData<Boolean> = _emptyResult

    fun fetchFavorites() {
        launch {
            val response = repository.getAll()
            _loading.postValue(true)
            delay(2000)

            if (response.isEmpty()){
                _emptyResult.postValue(true)
                _loading.postValue(false)
            }
            else {
                _favorites.postValue(response)
                _loading.postValue(false)

            }
        }
    }

    fun setFavorite(character: CharacterItem) = with(character) {
        isFavorite = isFavorite.not()

        try {
            if (isFavorite)
                repository.add(character)
            else
                repository.delete(character)
        } catch (ex: Exception) {
            //todo: adicionar tratamento
        }
    }
}