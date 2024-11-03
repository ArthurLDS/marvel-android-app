package br.com.cwi.marvelapp.presentation.feature.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Thumbnail
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.fetchFavorites()

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        ItemGrid()
                    }
                }
            }
        }
    }

    @Composable
    fun CharacterCard(
        characterItem: CharacterItem,
        onClickFavorite: (CharacterItem) -> Unit = {}
    ) {
        var isFavorite by remember { mutableStateOf(characterItem.isFavorite) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val painter = rememberAsyncImagePainter(
                    model = characterItem.getUrlImage(), contentScale = ContentScale.Crop
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    Text(
                        text = characterItem.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        ),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            onClick = {
                                isFavorite = isFavorite.not()
                                onClickFavorite(characterItem)
                            }
                        ) {
                            FavoriteIcon(isFavorite)
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun FavoriteIcon(isFavorite: Boolean) {
        val icon = painterResource(
            id = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
        Image(
            painter = icon,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
    }

    @Composable
    fun ItemGrid() {
        val loading by viewModel.loading.observeAsState(false)
        val favorites by viewModel.favorites.observeAsState(emptyList())

        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize() // Garante que o Box preencha toda a tela
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp), // Define o tamanho pequeno do indicador
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = favorites
                    ) { item ->
                        CharacterCard(item) {
                            viewModel.setFavorite(it)
                        }
                    }
                }
            }
        }
    }

    private fun setUpViewModel() {

        /*viewModel.favorites.observe(viewLifecycleOwner) { list ->
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
        }*/

        viewModel.fetchFavorites()
    }

    /*private fun setUpSwipeRefresh() {
        binding.srlFavorites.setOnRefreshListener { viewModel.fetchFavorites() }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            val layoutManager = GridLayoutManager(context, 2)

            rvFavorites.adapter = adapter
            rvFavorites.layoutManager = layoutManager
        }
    }*/

    /*private fun toggleViewFlipper(viewPosition: Int) {
        binding.vfFavorites.displayedChild = viewPosition
    }*/

    private fun onClickFavorite(item: CharacterItem) {
        viewModel.setFavorite(item)
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewCharacterCard() {
        val character = CharacterItem(
            id = 1,
            name = "3-D Man testeeeeeeeeeeeeeeee  eeeeeeee e e e e e e e  e e e  e e e ",
            thumbnail = Thumbnail(
                extension = "jpg",
                path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"
            ),
            isFavorite = false
        )
        CharacterCard(characterItem = character)
    }

}