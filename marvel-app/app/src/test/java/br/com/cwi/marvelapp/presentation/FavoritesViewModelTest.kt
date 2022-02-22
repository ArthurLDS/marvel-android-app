package br.com.cwi.marvelapp.presentation

import androidx.lifecycle.Observer
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.feature.favorite.FavoritesViewModel
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_ITEM
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_ITEM_FAVORITE
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest : BaseViewModelTest() {

    private val repository = mockk<CharacterRepository>()

    private val favoritesLiveDataObserver = mockk<Observer<List<CharacterItem>>>(relaxed = true)
    private val errorLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val emptyListLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val loadingLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `Given favorites characters When get all favorites Then return data successfully`() {
        val viewModel = instantiateViewModel()

        every { repository.getAll() } returns listOf(CHARACTER_ITEM)

        viewModel.fetchFavorites()

        verify {
            loadingLiveDataObserver.onChanged(true)
            favoritesLiveDataObserver.onChanged(listOf(CHARACTER_ITEM))
        }
        confirmVerified(
            emptyListLiveDataObserver,
            favoritesLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given empty favorites characters When get all favorites Then return empty result`() {
        val viewModel = instantiateViewModel()

        every { repository.getAll() } returns listOf()

        viewModel.fetchFavorites()

        verify {
            loadingLiveDataObserver.onChanged(true)
            emptyListLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            emptyListLiveDataObserver,
            favoritesLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given favorites characters When get all favorites Then return data with Error`() {
        val viewModel = instantiateViewModel()

        every { repository.getAll() } throws Exception()

        viewModel.fetchFavorites()

        verify {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            emptyListLiveDataObserver,
            favoritesLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given favorite character When set favorite Then delete from repository`() {
        val viewModel = instantiateViewModel()

        every { repository.delete(CHARACTER_ITEM) } returns Unit

        viewModel.setFavorite(CHARACTER_ITEM_FAVORITE)

        verify { repository.delete(CHARACTER_ITEM) }
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `Given not favorite character When set favorite Then add in repository`() {
        val viewModel = instantiateViewModel()

        every { repository.add(CHARACTER_ITEM_FAVORITE) } returns Unit

        viewModel.setFavorite(CHARACTER_ITEM_FAVORITE)

        verify { repository.add(CHARACTER_ITEM_FAVORITE) }
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    private fun instantiateViewModel(): FavoritesViewModel {
        val viewModel = FavoritesViewModel(repository)
        viewModel.favorites.observeForever(favoritesLiveDataObserver)
        viewModel.emptyResult.observeForever(emptyListLiveDataObserver)
        viewModel.error.observeForever(errorLiveDataObserver)
        viewModel.loading.observeForever(loadingLiveDataObserver)
        return viewModel
    }
}