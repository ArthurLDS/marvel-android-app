package br.com.cwi.marvelapp.presentation.feature.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel
import kotlinx.coroutines.async

class CharacterDetailViewModel(private val repository: CharacterRepository): BaseViewModel() {

    private val _data: MutableLiveData<CharacterItem> = MutableLiveData()
    val data: LiveData<CharacterItem> = _data

    private val _series: MutableLiveData<List<Item>> = MutableLiveData()
    val series: LiveData<List<Item>> = _series

    private val _comics: MutableLiveData<List<Item>> = MutableLiveData()
    val comics: LiveData<List<Item>> = _comics

    fun fetchCharacter(id: Long) {
        launch {
            _data.postValue(repository.getCharacterDetail(id))
        }
    }

    fun fetchComics(id: Long) {
        launch {
            val response = repository.getComics(id)
            _comics.postValue(response)
        }
    }
    fun fetchSeries(id: Long) {
        launch {
            val response = repository.getSeries(id)
            _series.postValue(response)
        }
    }

    fun setFavorite() = _data.value?.let { character ->
        character.isFavorite = character.isFavorite.not()
        if (character.isFavorite)
            repository.add(character)
        else
            repository.delete(character)
    }

    /*sealed class COmic {
        object ServiceUnavailable : OfferFlowEvent()
        object ShowFormScreen : OfferFlowEvent()
    }*/


}