package br.com.cwi.marvelapp.presentation.feature.characterdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ActivityCharacterDetailBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.presentation.extension.loadImage
import br.com.cwi.marvelapp.presentation.extension.showAlertDialog
import br.com.cwi.marvelapp.presentation.extension.visible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"

class CharacterDetailActivity : AppCompatActivity() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: ActivityCharacterDetailBinding

    private val adapterComics = SerieAdapter()

    private val adapterSeries = SerieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewModel()
        setUpRecyclerViewComics()
        setUpRecyclerViewSeries()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpViewModel() {
        val id = intent.getLongExtra(EXTRA_CHARACTER_ID, 0)

        viewModel.data.observe(this) {
            loadCharacterDetails(it)
        }
        viewModel.series.observe(this) {
            loadSeries(it)
        }
        viewModel.comics.observe(this) {
            loadComics(it)
        }

        viewModel.error.observe(this) {
            showAlertDialog(getString(R.string.txt_error_detail), false) { _, _ ->
                finish()
            }
        }

        viewModel.fetchCharacter(id)
        viewModel.fetchComics(id)
        viewModel.fetchSeries(id)
    }

    private fun loadCharacterDetails(character: CharacterItem) {
        with(binding) {
            ivCharacter.loadImage(character.getUrlImage())
            toolbarLayout.title = character.name
            contentScrolling.tvCharacterDescription.text = character.description.ifEmpty {
                getString(R.string.txt_no_description_found)
            }
            setUpFabFavorite(character)
        }
    }

    private fun setUpFabFavorite(character: CharacterItem) {
        with(binding.fabFavorite) {
            changeFabIcon(this, character.isFavorite)
            setOnClickListener { view ->
                viewModel.setFavorite()
                changeFabIcon(this, character.isFavorite)
                val message =
                    if (character.isFavorite) getString(R.string.txt_favorite_character)
                    else getString(R.string.txt_no_favorite_character)

                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(message, null).show()
            }
        }
    }

    private fun loadComics(list: List<Item>) {
        if (list.isEmpty())
            binding.contentScrolling.tvComicsMessage.visible()
        else
            adapterComics.updateItems(list)
    }

    private fun loadSeries(list: List<Item>) {
        if (list.isEmpty())
            binding.contentScrolling.tvSeriesMessage.visible()
        else
            adapterSeries.updateItems(list)
    }

    private fun setUpRecyclerViewComics() {
        with(binding) {
            contentScrolling.rvComics.adapter = adapterComics
            contentScrolling.rvComics.layoutManager = LinearLayoutManager(
                this@CharacterDetailActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    private fun setUpRecyclerViewSeries() {
        with(binding) {
            contentScrolling.rvSeries.adapter = adapterSeries
            contentScrolling.rvSeries.layoutManager = LinearLayoutManager(
                this@CharacterDetailActivity,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    private fun changeFabIcon(fab: FloatingActionButton, isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        fab.setImageDrawable(getDrawable(icon))
    }
}