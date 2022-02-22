package br.com.cwi.marvelapp.presentation.feature.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel

class CharacterDetailViewModel(private val repository: CharacterRepository) : BaseViewModel() {

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
            _comics.postValue(repository.getComics(id))
        }
    }

    fun fetchSeries(id: Long) {
        launch {
            _series.postValue(repository.getSeries(id))
        }
    }

    fun setFavorite() = _data.value?.let { character ->
        character.isFavorite = character.isFavorite.not()
        if (character.isFavorite)
            repository.add(character)
        else
            repository.delete(character)
    }

}