package br.com.cwi.marvelapp.presentation.feature.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.base.BaseViewModel

class CharacterDetailViewModel(private val repository: CharacterRepository): BaseViewModel() {

    private val _data: MutableLiveData<CharacterItem> = MutableLiveData()
    val data: LiveData<CharacterItem> = _data

    fun fetchCharacter(id: Long) {
        launch {
            val response = repository.getCharacterDetail(id)
            _data.postValue(response)
        }
    }

}