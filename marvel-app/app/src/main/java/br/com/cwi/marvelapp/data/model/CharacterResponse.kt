package br.com.cwi.marvelapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class CharactersResponse(
    val data: CharacterDataResponse
)

data class CharacterDataResponse(
    val results: List<CharacterItemResponse>,
    val total: Int
)

@Parcelize
@Entity(tableName = "characters_table")
data class CharacterItemResponse(
    @PrimaryKey val id: Long,
    val name: String = "",
    val description: String = "",
    var isFavorite : Boolean = false,
    val series: SeriesResponse? = null,
    val thumbnail: ThumbnailResponse? = null,
    val comics: ComicsResponse? = null
) : Parcelable

@Parcelize
data class ComicsResponse(
    val available: String = "",
    val collectionURI: String = "",
    val items: List<ItemResponse>? = null,
    val returned: String = ""
) : Parcelable

@Parcelize
data class SeriesResponse(
    val available: String = "",
    val collectionURI: String = "",
    val items: List<ItemResponse>? = null,
    val returned: String = ""
) : Parcelable

@Parcelize
data class ItemResponse(
    val name: String = "",
    val resourceURI: String = "",
    val type: String = ""
) : Parcelable

@Parcelize
data class ThumbnailResponse(
    val extension: String = "",
    val path: String = ""
) : Parcelable