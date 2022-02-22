package br.com.cwi.marvelapp.data

import br.com.cwi.marvelapp.data.model.local.CharacterItemLocal
import br.com.cwi.marvelapp.data.source.local.CharacterDao

class CharacterDaoTestMock: CharacterDao {

    private val list = mutableListOf<CharacterItemLocal>()

    override fun add(character: CharacterItemLocal) {
        character.isFavorite = true
        list.add(character)
    }

    override fun getAll(): List<CharacterItemLocal> = list

    override fun getCharacterById(id: Long): CharacterItemLocal? {
        return list.firstOrNull { it.id == id }
    }

    override fun delete(character: CharacterItemLocal) {
        character.isFavorite = false
        list.filter { character.id != it.id }
    }
}