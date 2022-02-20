package br.com.cwi.marvelapp.presentation.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.databinding.CharactersFragmentBinding
import br.com.cwi.marvelapp.presentation.extension.visibleOrGone
import org.koin.androidx.viewmodel.ext.android.viewModel


const val SHOW_CONTENT = 0
const val SHOW_LOADING = 1
const val SHOW_ERROR = 2

class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModel()

    private lateinit var binding: CharactersFragmentBinding

    private val adapter = CharacterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewModel()
        setUpSwipeRefresh()
        setUpSearchField()
    }

    private fun setUpViewModel() {
        viewModel.fetchCharacters()

        viewModel.charactersLiveData.observe(viewLifecycleOwner) { onLoadCharacterList(it) }

        viewModel.loading.observe(viewLifecycleOwner) { toggleViewFlipper(SHOW_LOADING) }

        viewModel.error.observe(viewLifecycleOwner) {
            toggleViewFlipper(SHOW_ERROR)
            binding.srlCharacters.isRefreshing = false
        }
    }

    private fun setUpSwipeRefresh() {
        binding.srlCharacters.setOnRefreshListener { viewModel.refreshCharacters() }
    }

    private fun setUpSearchField() {
        binding.titSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchCharacters(text.toString())
        }
    }

    private fun onLoadCharacterList(list: List<Character>) {
        with(binding) {
            progressBar.visibleOrGone(false)
            toggleViewFlipper(SHOW_CONTENT)
            adapter.addItems(list)
            srlCharacters.isRefreshing = false
        }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            val layoutManager = GridLayoutManager(context, 2)

            rvCharacters.adapter = adapter
            rvCharacters.layoutManager = layoutManager

            setupScrollListener()
        }
    }

    private fun setupScrollListener() {
        val scrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val linearLayout = recyclerView.layoutManager as GridLayoutManager
                        val lastItemListPosition =
                            linearLayout.findLastCompletelyVisibleItemPosition()
                        val lastItemPosition = recyclerView.adapter?.itemCount?.minus(1)

                        if (lastItemListPosition == lastItemPosition) {
                            viewModel.fetchCharacters(nextPage = true)
                            binding.progressBar.visibleOrGone(true)
                        }
                    }
                }
            }

        binding.rvCharacters.removeOnScrollListener(scrollListener)
        binding.rvCharacters.addOnScrollListener(scrollListener)
    }

    private fun toggleViewFlipper(viewPosition: Int) {
        binding.vfCharacter.displayedChild = viewPosition

    }
}