package br.com.cwi.marvelapp.presentation

import androidx.lifecycle.Observer
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import br.com.cwi.marvelapp.presentation.feature.characters.CharactersViewModel
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_DATA
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_DATA_EMPTY
import br.com.cwi.marvelapp.testData.TestData.CHARACTER_ITEM
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest : BaseViewModelTest() {

    private val repository = mockk<CharacterRepository>()

    private val characterLiveDataObserver = mockk<Observer<List<CharacterItem>>>(relaxed = true)
    private val errorLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val loadingLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)
    private val emptyResultLiveDataObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `given first page load when fetch characters then return list `() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 0) } returns CHARACTER_DATA

        viewModel.fetchCharacters()

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            characterLiveDataObserver.onChanged(listOf(CHARACTER_ITEM))
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given first page load when fetch characters with error then show error `() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 0) } throws Exception()

        viewModel.fetchCharacters()

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }


    @Test
    fun `given second page load when fetch characters then return list `() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 20) } returns CHARACTER_DATA

        viewModel.fetchCharactersWithPagination()

        coVerifyOrder {
            characterLiveDataObserver.onChanged(listOf(CHARACTER_ITEM))
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given second page load when fetch characters with error Then show error `() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 20) } throws Exception()

        viewModel.fetchCharactersWithPagination()

        coVerifyOrder {
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given search term with more than 4 letters when search character then return characters `() {
        val viewModel = instantiateViewModel()
        val termSearch = "Spid"

        coEvery { repository.getCharacters(20, 0, termSearch) } returns CHARACTER_DATA

        viewModel.searchCharacters(termSearch)

        coVerify {
            characterLiveDataObserver.onChanged(listOf(CHARACTER_ITEM))
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given search term with less than 4 letters when search character then do nothing `() {
        val viewModel = instantiateViewModel()
        val termSearch = "Spi"

        coEvery { repository.getCharacters(20, 0, termSearch) } returns CHARACTER_DATA

        viewModel.searchCharacters(termSearch)

        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given search when search character with then show error `() {
        val viewModel = instantiateViewModel()
        val termSearch = "Spider"

        coEvery { repository.getCharacters(20, 0, termSearch) } throws Exception()

        viewModel.searchCharacters(termSearch)

        coVerify {
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given search term with more than 4 letters when search empty response then return show empty result `() {
        val viewModel = instantiateViewModel()
        val termSearch = "Spider"

        coEvery { repository.getCharacters(20, 0, termSearch) } returns CHARACTER_DATA_EMPTY

        viewModel.searchCharacters(termSearch)

        coVerify {
            emptyResultLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            emptyResultLiveDataObserver,
            characterLiveDataObserver,
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given character not favorite when set favorite then add in repository`() {
        val viewModel = instantiateViewModel()

        every { repository.add(CHARACTER_ITEM) } returns Unit

        viewModel.setFavorite(CHARACTER_ITEM)

        verify { repository.add(CHARACTER_ITEM) }
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given character favorite when set favorite then delete in repository `() {
        val viewModel = instantiateViewModel()

        every { repository.delete(CHARACTER_ITEM) } returns Unit

        viewModel.setFavorite(CHARACTER_ITEM)

        verify { repository.delete(CHARACTER_ITEM) }
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given characters when refresh list then get list first page`() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 0, null) } returns CHARACTER_DATA

        viewModel.refreshCharacters()

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            characterLiveDataObserver.onChanged(listOf(CHARACTER_ITEM))
        }
        confirmVerified(characterLiveDataObserver)
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver
        )
    }

    @Test
    fun `given characters when refresh list with Error then show error`() {
        val viewModel = instantiateViewModel()

        coEvery { repository.getCharacters(20, 0, null) } throws Exception()

        viewModel.refreshCharacters()

        coVerifyOrder {
            loadingLiveDataObserver.onChanged(true)
            errorLiveDataObserver.onChanged(true)
        }
        confirmVerified(
            errorLiveDataObserver,
            loadingLiveDataObserver,
            characterLiveDataObserver
        )
    }

    private fun instantiateViewModel(): CharactersViewModel {
        val viewModel = CharactersViewModel(repository)
        viewModel.characters.observeForever(characterLiveDataObserver)
        viewModel.error.observeForever(errorLiveDataObserver)
        viewModel.loading.observeForever(loadingLiveDataObserver)
        viewModel.emptyResult.observeForever(emptyResultLiveDataObserver)
        return viewModel
    }

}