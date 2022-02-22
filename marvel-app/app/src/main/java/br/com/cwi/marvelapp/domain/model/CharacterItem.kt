package br.com.cwi.marvelapp.domain.model

data class CharacterItem(
    val id: Long,
    val name: String = "",
    val description: String = "",
    var isFavorite: Boolean = false,
    val series: Series? = null,
    val thumbnail: Thumbnail? = null,
    val comics: Comics? = null
) {
    fun getUrlImage() = "${thumbnail?.path}.${thumbnail?.extension}"
}