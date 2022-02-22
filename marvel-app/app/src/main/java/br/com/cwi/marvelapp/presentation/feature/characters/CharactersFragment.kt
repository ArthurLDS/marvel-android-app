package br.com.cwi.marvelapp.presentation.feature.characters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.CharactersFragmentBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.presentation.feature.characterdetail.CharacterDetailActivity
import br.com.cwi.marvelapp.presentation.feature.characterdetail.EXTRA_CHARACTER_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SHOW_CONTENT = 0
const val SHOW_LOADING = 1
const val SHOW_ERROR = 2

class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModel()

    private lateinit var binding: CharactersFragmentBinding

    private val adapter = CharacterAdapter(
        onClickItem = ::onClickCharacter,
        onClickFavorite = ::onClickFavorite
    )

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
        setUpSwipeRefresh()
        setUpSearchField()
        setUpListButtons()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel.characters.observe(viewLifecycleOwner) { onLoadCharacterList(it) }

        viewModel.loading.observe(viewLifecycleOwner) { toggleViewFlipper(SHOW_LOADING) }

        viewModel.emptyResult.observe(viewLifecycleOwner) { toggleViewFlipperSearch(SHOW_ERROR) }

        viewModel.error.observe(viewLifecycleOwner) {
            toggleViewFlipper(SHOW_ERROR)
            binding.srlCharacters.isRefreshing = false
        }

        viewModel.fetchCharacters()
    }

    private fun setUpListButtons() {
        viewModel.isGridList.observe(viewLifecycleOwner) { setUpButtonListIcon(it) }

        binding.viewSearchHeader.btnGrid.setOnClickListener {
            viewModel.setCharactersTypeList(true)
        }
        binding.viewSearchHeader.btnList.setOnClickListener {
            viewModel.setCharactersTypeList(false)
        }
    }

    private fun setUpSwipeRefresh() {
        binding.srlCharacters.setOnRefreshListener {
            binding.viewSearchHeader.titSearch.text = null
            viewModel.refreshCharacters()
        }
    }

    private fun setUpSearchField() {
        binding.viewSearchHeader.titSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchCharacters(text.toString())
        }
    }

    private fun onLoadCharacterList(list: List<CharacterItem>) {
        with(binding) {
            toggleViewFlipper(SHOW_CONTENT)
            toggleViewFlipperSearch(SHOW_CONTENT)
            adapter.updateItems(list)
            srlCharacters.isRefreshing = false
        }
    }

    private fun onClickCharacter(id: Long) {
        startActivity(
            Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra(EXTRA_CHARACTER_ID, id)
            }
        )
    }

    private fun onClickFavorite(item: CharacterItem) {
        viewModel.setFavorite(item)
    }

    private fun setUpRecyclerView(isGridList: Boolean = true) {
        with(binding) {
            val layoutManager = if (isGridList)
                GridLayoutManager(context, 2)
            else
                LinearLayoutManager(context)

            rvCharacters.adapter = adapter
            rvCharacters.layoutManager = layoutManager

            setupScrollListener()
        }
    }

    private fun setUpButtonListIcon(isGrid: Boolean){
        context?.let {
            setUpRecyclerView(isGrid)
            binding.viewSearchHeader.btnGrid.setImageDrawable(
                ContextCompat.getDrawable(
                    it,
                    if (isGrid) R.drawable.ic_grid
                    else R.drawable.ic_grid_border
                )
            )
            binding.viewSearchHeader.btnList.setImageDrawable(
                ContextCompat.getDrawable(
                    it,
                    if (isGrid) R.drawable.ic_list_border
                    else R.drawable.ic_list_icon
                )
            )
        }
    }

    private fun setupScrollListener() {
        val scrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val layout = recyclerView.layoutManager as LinearLayoutManager
                        val lastItemListPosition = layout.findLastCompletelyVisibleItemPosition()
                        val lastItemPosition = recyclerView.adapter?.itemCount?.minus(1)

                        if (lastItemListPosition == lastItemPosition) {
                            viewModel.fetchCharactersWithPagination()
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

    private fun toggleViewFlipperSearch(viewPosition: Int) {
        binding.vfCharacterSearch.displayedChild = viewPosition

    }
}