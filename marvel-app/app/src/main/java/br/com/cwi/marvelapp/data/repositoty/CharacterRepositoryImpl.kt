package br.com.cwi.marvelapp.data.repositoty

import br.com.cwi.marvelapp.data.mapper.CharacterItemLocalMapper
import br.com.cwi.marvelapp.data.mapper.CharacterItemMapper
import br.com.cwi.marvelapp.data.mapper.CharacterMapper
import br.com.cwi.marvelapp.data.source.local.CharacterDao
import br.com.cwi.marvelapp.data.source.remote.MarvelApi
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val dao: CharacterDao,
    private val api: MarvelApi,
    private val mapperCharacterData: CharacterMapper,
    private val mapperCharacterItem: CharacterItemMapper,
    private val mapperCharacterLocal: CharacterItemLocalMapper,
) : CharacterRepository {

    // Remote
    override suspend fun getCharacters(limit: Int, offset: Int, term: String?) =
        withContext(Dispatchers.IO) {
            val characters =
                mapperCharacterData.toDomain(api.getCharacters(limit, offset, term).data)

            characters.results.forEach {
                val favorite = getCharacterById(it.id)
                if (favorite != null) it.isFavorite = true
            }
            characters
        }


    override suspend fun getCharacterDetail(id: Long) = withContext(Dispatchers.IO) {
        val detail = api.getCharacterDetail(id)

        detail.data.results.firstOrNull()?.let {
            val character = mapperCharacterItem.toDomain(it)

            character.apply {
                isFavorite = getCharacterById(id)?.isFavorite ?: false
            }
        }
    }


    override suspend fun getSeries(id: Long): List<Item> {
        val items = mutableListOf<Item>()

        val series = api.getSeries(id).data.results

        series.forEach {
            items.add(
                Item(
                    name = it.title,
                    resourceURI = "${it.thumbnail.path}.${it.thumbnail.extension}"
                )
            )
        }
        return items
    }

    override suspend fun getComics(id: Long): List<Item> {
        val items = mutableListOf<Item>()
        val comics = api.getComics(id).data.results

        comics.map {
            items.add(
                Item(
                    name = it.title,
                    resourceURI = "${it.thumbnail.path}.${it.thumbnail.extension}"
                )
            )
        }
        return items
    }


    // Local
    override fun add(character: CharacterItem) {
        dao.add(mapperCharacterLocal.fromDomain(character))
    }

    override fun delete(character: CharacterItem) {
        dao.delete(mapperCharacterLocal.fromDomain(character))
    }

    override fun getAll(): List<CharacterItem> = mapperCharacterLocal.toDomain(dao.getAll())

    override fun getCharacterById(id: Long): CharacterItem? =
        dao.getCharacterById(id)?.let {
            mapperCharacterLocal.toDomain(it)
        }

}