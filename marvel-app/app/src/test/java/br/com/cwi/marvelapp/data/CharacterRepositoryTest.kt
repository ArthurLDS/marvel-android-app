package br.com.cwi.marvelapp.data

import br.com.cwi.marvelapp.data.mapper.CharacterItemLocalMapper
import br.com.cwi.marvelapp.data.mapper.CharacterItemMapper
import br.com.cwi.marvelapp.data.mapper.CharacterMapper
import br.com.cwi.marvelapp.data.repositoty.CharacterRepositoryImpl
import br.com.cwi.marvelapp.data.source.remote.MarvelApi
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_LOCAL
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_RESPONSE_EMPTY_LIST
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_RESPONSE_SIMPLE_LIST
import br.com.cwi.marvelapp.testData.TestData.SERIES_RESPONSE_EMPTY_LIST
import br.com.cwi.marvelapp.testData.TestData.SERIES_RESPONSE_MULTIPLE_LIST
import br.com.cwi.marvelapp.testData.TestData.SERIES_RESPONSE_SIMPLE_LIST
import br.com.cwi.marvelapp.testData.TestData.THUMBNAIL_SERIES
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CharacterRepositoryTest {

    private val apiService: MarvelApi = mockk()
    private val daoService = CharacterDaoTestMock()
    private val mapperCharacterData = CharacterMapper()
    private val mapperCharacterItem = CharacterItemMapper()
    private val mapperCharacterLocal = CharacterItemLocalMapper()

    private lateinit var repository: CharacterRepository

    @Before
    fun setup() {
        repository = instantiateRepository()
    }

    @Test
    fun `Given filters When get characters Then verify first character list`() {
        runBlocking {
            //Given
            val id: Long = 23
            coEvery { apiService.getCharacters(20, 0, null) } returns CHARACTER_RESPONSE_SIMPLE_LIST

            //When
            val response = repository.getCharacters(20, 0, null)

            //Then
            val expected = response.results.first() ==
                    mapperCharacterItem.toDomain(CHARACTER_RESPONSE_SIMPLE_LIST.data.results.first()) &&
                    response.results.size == 1
            assert(expected)
        }
    }

    @Test
    fun `Given filters When get characters with error Then verify error`() {
        runBlocking {
            //Given
            val exception = Exception("Erro no servidor")
            coEvery { apiService.getCharacters(20, 0, null) } throws exception

            // When & Then
            runCatching { repository.getCharacters(20, 0, null) }
                .onFailure { error ->
                    assert(error.message == exception.message)
                }
        }
    }

    @Test
    fun `Given favorite character When get characters Then verify first favorite character`() {
        runBlocking {
            //Given
            coEvery { apiService.getCharacters(20, 0, null) } returns CHARACTER_RESPONSE_SIMPLE_LIST
            daoService.add(CHARACTER_LOCAL)

            //When
            val response = repository.getCharacters(20, 0, null)

            //Then
            assert(response.results.first().isFavorite)
        }
    }

    @Test
    fun `Given not favorite character When get characters Then verify first not favorite character`() {
        runBlocking {
            //Given
            coEvery { apiService.getCharacters(20, 0, null) } returns CHARACTER_RESPONSE_SIMPLE_LIST

            //When
            val response = repository.getCharacters(20, 0, null)

            //Then
            assert(response.results.first().isFavorite.not())
        }
    }

    @Test
    fun `Given character id When get character details Then verify character`() = runBlocking {
        //Given
        val id: Long = 23
        coEvery { apiService.getCharacterDetail(id) } returns CHARACTER_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getCharacterDetail(id)

        //Then
        val expected = response?.name == CHARACTER_RESPONSE_SIMPLE_LIST.data.results.first().name
        assert(expected)
    }

    @Test
    fun `Given character id When get character details with erro Then verify error`() {
        runBlocking {
            //Given
            val exception = Exception("Erro no servidor")
            val id: Long = 23
            coEvery { apiService.getCharacterDetail(id) } throws exception

            //When & Then
            runCatching {
                repository.getCharacterDetail(id)
            }.onFailure {
                assert(it.message == exception.message)
            }
        }
    }

    @Test
    fun `Given favorite character When get character details Then verify character domain is favorite`() =
        runBlocking {
            //Given
            val id: Long = 23
            coEvery { apiService.getCharacterDetail(id) } returns CHARACTER_RESPONSE_SIMPLE_LIST
            daoService.add(CHARACTER_LOCAL)

            //When
            val response = repository.getCharacterDetail(id)

            //Then
            assert(response?.isFavorite == true)
        }

    @Test
    fun `Given not favorite character When get character details Then verify character domain is not favorite`() =
        runBlocking {
            //Given
            val id: Long = 23
            coEvery { apiService.getCharacterDetail(id) } returns CHARACTER_RESPONSE_SIMPLE_LIST
            daoService.add(CHARACTER_LOCAL)
            daoService.delete(CHARACTER_LOCAL)

            //When
            val response = repository.getCharacterDetail(id)

            //Then
            assert(response?.isFavorite == false)
        }

    @Test
    fun `Given character id When get character details Then verify empty character`() =
        runBlocking {
            //Given
            val id: Long = 23
            coEvery { apiService.getCharacterDetail(id) } returns CHARACTER_RESPONSE_EMPTY_LIST

            //When
            val response = repository.getCharacterDetail(id)

            //Then
            assert(response == null)
        }

    @Test
    fun `Given character id When get character comics Then verify first item list`() =
        runBlockingTest {
            //Given
            val id: Long = 123
            coEvery { apiService.getComics(id) } returns SERIES_RESPONSE_SIMPLE_LIST

            //When
            val response = repository.getComics(id)

            //Then
            assertEquals(
                response.first().name,
                SERIES_RESPONSE_SIMPLE_LIST.data.results.first().title
            )
        }

    @Test
    fun `Given id When get character comics Then verify first second list`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getComics(id) } returns SERIES_RESPONSE_MULTIPLE_LIST

        //When
        val response = repository.getComics(id)

        //Then
        assertEquals(response[1].name, SERIES_RESPONSE_MULTIPLE_LIST.data.results[1].title)
    }

    @Test
    fun `Given id When get character empty comics Then verify list`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getComics(id) } returns SERIES_RESPONSE_EMPTY_LIST

        //When
        val response = repository.getComics(id)

        //Then
        assert(response.isEmpty())
    }

    @Test
    fun `Given id When get character comics Then verify thumbnail URI`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getComics(id) } returns SERIES_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getComics(id)

        //Then
        val expected =
            response.first().resourceURI == "${THUMBNAIL_SERIES.path}.${THUMBNAIL_SERIES.extension}"
        assert(expected)
    }

    @Test
    fun `Given id When get character comics Then verify wrong thumbnail URI`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getComics(id) } returns SERIES_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getComics(id)

        //Then
        val expected =
            response.first().resourceURI != "${THUMBNAIL_SERIES.extension}.${THUMBNAIL_SERIES.path}"
        assert(expected)
    }

    @Test
    fun `Given id When get character Series Then verify first item list`() = runBlockingTest {
        //Given
        val id: Long = 123
        coEvery { apiService.getSeries(id) } returns SERIES_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getSeries(id)

        //Then
        assertEquals(response.first().name, SERIES_RESPONSE_SIMPLE_LIST.data.results.first().title)
    }

    @Test
    fun `Given id When get character Series Then verify first second list`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getSeries(id) } returns SERIES_RESPONSE_MULTIPLE_LIST

        //When
        val response = repository.getSeries(id)

        //Then
        assertEquals(response[1].name, SERIES_RESPONSE_MULTIPLE_LIST.data.results[1].title)
    }

    @Test
    fun `Given id When get character empty Series Then verify list`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getSeries(id) } returns SERIES_RESPONSE_EMPTY_LIST

        //When
        val response = repository.getSeries(id)

        //Then
        assert(response.isEmpty())
    }

    @Test
    fun `Given id When get character Series Then verify thumbnail URI`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getSeries(id) } returns SERIES_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getSeries(id)

        //Then
        val expected =
            response.first().resourceURI == "${THUMBNAIL_SERIES.path}.${THUMBNAIL_SERIES.extension}"
        assert(expected)
    }

    @Test
    fun `Given id When get character Series Then verify wrong thumbnail URI`() = runBlockingTest {
        //Given
        val id: Long = 124
        coEvery { apiService.getSeries(id) } returns SERIES_RESPONSE_SIMPLE_LIST

        //When
        val response = repository.getSeries(id)

        //Then
        val expected =
            response.first().resourceURI != "${THUMBNAIL_SERIES.extension}.${THUMBNAIL_SERIES.path}"
        assert(expected)
    }

    @Test
    fun `Given character id When get local character by id Then return character`() {
        //Given
        daoService.add(CHARACTER_LOCAL)

        //When
        val response = daoService.getCharacterById(CHARACTER_LOCAL.id)

        //Then
        assert(response == CHARACTER_LOCAL)
    }

    @Test
    fun `Given character id When get local character by wrong id Then return null`() {
        //Given
        daoService.add(CHARACTER_LOCAL)

        //When
        val response = daoService.getCharacterById(1234)

        //Then
        assert(response == null)
    }

    private fun instantiateRepository() = CharacterRepositoryImpl(
        daoService,
        apiService,
        mapperCharacterData,
        mapperCharacterItem,
        mapperCharacterLocal
    )

}