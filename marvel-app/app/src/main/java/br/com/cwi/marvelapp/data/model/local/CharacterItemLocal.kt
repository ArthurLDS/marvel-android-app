package br.com.cwi.marvelapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterItemLocal(
    @PrimaryKey val id: Long,
    val name: String = "",
    val description: String? = "",
    var isFavorite : Boolean? = false,
    val thumbnail: ThumbnailLocal? = null
)

data class ThumbnailLocal(
    val extension: String = "",
    val path: String = ""
)