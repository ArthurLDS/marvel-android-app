package br.com.cwi.marvelapp.presentation.feature.characterdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.data.model.CharacterItemResponse
import br.com.cwi.marvelapp.databinding.ActivityCharacterDetailBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.presentation.extension.loadImage
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"

class CharacterDetailActivity : AppCompatActivity() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val id = intent.getLongExtra(EXTRA_CHARACTER_ID, 0)
        viewModel.fetchCharacter(id)

        viewModel.data.observe(this) { setUpCharacterData(it) }
    }

    private fun setUpCharacterData(character: CharacterItem) {
        with(binding) {

            ivCharacter.loadImage("${character.thumbnail?.path}.${character.thumbnail?.extension}")
            toolbarLayout.title = character.name
            contentScrolling.tvCharacterDescription.text = character.description

            fab.setOnClickListener { view ->
                Snackbar.make(view, character.description, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }
}