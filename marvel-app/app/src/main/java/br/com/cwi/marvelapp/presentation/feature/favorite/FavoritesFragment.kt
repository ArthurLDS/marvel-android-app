package br.com.cwi.marvelapp.presentation.feature.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.cwi.marvelapp.databinding.FavoritesFragmentBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SHOW_CONTENT = 0
const val SHOW_LOADING = 1
const val SHOW_ERROR = 2
const val SHOW_EMPTY_LIST = 3

class FavoritesFragment : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter = FavoritesAdapter(
        onClickFavorite = ::onClickFavorite
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoritesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpSwipeRefresh()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            with(binding) {
                toggleViewFlipper(SHOW_CONTENT)
                adapter.addItems(list)
                srlFavorites.isRefreshing = false
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { toggleViewFlipper(SHOW_LOADING) }

        viewModel.emptyResult.observe(viewLifecycleOwner) {
            toggleViewFlipper(SHOW_EMPTY_LIST)
            binding.srlFavorites.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner) {
            toggleViewFlipper(SHOW_ERROR)
            binding.srlFavorites.isRefreshing = false
        }

        viewModel.fetchFavorites()
    }

    private fun setUpSwipeRefresh() {
        binding.srlFavorites.setOnRefreshListener { viewModel.fetchFavorites() }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            val layoutManager = GridLayoutManager(context, 2)

            rvFavorites.adapter = adapter
            rvFavorites.layoutManager = layoutManager
        }
    }

    private fun toggleViewFlipper(viewPosition: Int) {
        binding.vfFavorites.displayedChild = viewPosition
    }

    private fun onClickFavorite(item: CharacterItem) { viewModel.setFavorite(item) }

}