package br.com.cwi.marvelapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.cwi.marvelapp.data.model.local.CharacterItemLocal

@Dao
interface CharacterDao {

    @Insert
    fun add(character: CharacterItemLocal)

    @Query("SELECT * FROM character_table")
    fun getAll(): List<CharacterItemLocal>

    @Query("SELECT * FROM character_table WHERE id=:id")
    fun getCharacterById(id: Long): CharacterItemLocal?

    @Delete
    fun delete(character: CharacterItemLocal)

}