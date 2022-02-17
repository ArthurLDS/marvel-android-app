package br.com.cwi.marvelapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ImageViewCompat
import br.com.cwi.marvelapp.R
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

        viewModel.fetchCharacters()
    }

    private fun setUpViewModel() {

        val tvHello = findViewById<AppCompatTextView>(R.id.tv_hello)
        val ivCharacter = findViewById<AppCompatImageView>(R.id.iv_character)

        viewModel.charactersLiveData.observe(this) { characters ->
            tvHello.text = characters.toString()
        }

        viewModel.characterDetailLiveData.observe(this) { character ->
            tvHello.text = character.toString()

            Glide.with(this)
                .load("${character.thumbnail?.path}.${character.thumbnail?.extension}")
                .into(ivCharacter)
        }
    }
}