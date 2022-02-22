package br.com.cwi.marvelapp.presentation

import androidx.lifecycle.Observer
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.feature.characterdetail.CharacterDetailViewModel
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_ITEM
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_ITEM_FAVORITE
import br.com.cwi.marvelapp.testData.TestData.SERIES_DATA
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest : BaseViewModelTest() {

    private val repository = mockk<CharacterRepository>()

    private val dataLiveDataObserver = mockk<Observer<CharacterItem>>(relaxed = true)
    private val seriesLiveDataObserver = mockk<Observer<List<Item>>>(relaxed = true)
    private val comicsLiveDataObserver = mockk<Observer<List<Item>>>(relaxed = true)
    private val errorLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val loadingLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `Given character id When fetch character details Then load data successfully`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getCharacterDetail(id) } returns CHARACTER_ITEM

        viewModel.fetchCharacter(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            dataLiveDataObserver.onChanged(CHARACTER_ITEM)
        }
        confirmVerified(
            dataLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given character id When fetch character details with error Then show error`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getCharacterDetail(id) } throws Exception()

        viewModel.fetchCharacter(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            dataLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given character id When fetch comics Then load data`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getComics(id) } returns listOf(SERIES_DATA)

        viewModel.fetchComics(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            comicsLiveDataObserver.onChanged(listOf(SERIES_DATA))
        }
        confirmVerified(
            comicsLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given character id When fetch comics with error Then show error`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getComics(id) } throws Exception()

        viewModel.fetchComics(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            comicsLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given character id When fetch series Then load data`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getSeries(id) } returns listOf(SERIES_DATA)

        viewModel.fetchSeries(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            seriesLiveDataObserver.onChanged(listOf(SERIES_DATA))
        }
        confirmVerified(
            seriesLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given character id When fetch series with error Then show error`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        coEvery { repository.getSeries(id) } throws Exception()

        viewModel.fetchSeries(id)

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            comicsLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given favorite character When set favorite Then delete from repository`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        every { repository.delete(CHARACTER_ITEM) } returns Unit
        coEvery { repository.getCharacterDetail(id) } returns CHARACTER_ITEM_FAVORITE
        viewModel.fetchCharacter(123)

        viewModel.setFavorite()

        verify { repository.delete(CHARACTER_ITEM) }
    }

    @Test
    fun `Given not favorite character When set favorite Then add in repository`() {
        val viewModel = instantiateViewModel()
        val id: Long = 123

        every { repository.add(CHARACTER_ITEM_FAVORITE) } returns Unit
        coEvery { repository.getCharacterDetail(id) } returns CHARACTER_ITEM_FAVORITE
        viewModel.fetchCharacter(123)

        viewModel.setFavorite()

        verify { repository.add(CHARACTER_ITEM_FAVORITE) }
    }

    private fun instantiateViewModel(): CharacterDetailViewModel {
        val viewModel = CharacterDetailViewModel(repository)
        viewModel.data.observeForever(dataLiveDataObserver)
        viewModel.comics.observeForever(comicsLiveDataObserver)
        viewModel.series.observeForever(seriesLiveDataObserver)
        viewModel.error.observeForever(errorLiveDataObserver)
        viewModel.loading.observeForever(loadingLiveDataObserver)
        return viewModel
    }
}