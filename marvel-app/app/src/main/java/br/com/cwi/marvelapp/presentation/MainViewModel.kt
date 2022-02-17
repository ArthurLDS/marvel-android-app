package br.com.cwi.marvelapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.domain.CharacterRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _charactersLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    val charactersLiveData: LiveData<List<Character>> = _charactersLiveData

    private val _characterDetailLiveData: MutableLiveData<Character> = MutableLiveData()
    val characterDetailLiveData: LiveData<Character> = _characterDetailLiveData

    fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val response: Character = repository.getCharacterDetail(1011198)

                _characterDetailLiveData.postValue(response)

            } catch (ex: Exception) {
                println("OCORREU UM ERRO AI! $ex")
                throw ex
            }
        }
    }

}