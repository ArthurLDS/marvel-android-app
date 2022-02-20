package br.com.cwi.marvelapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ActivityCharacterDetailBinding
import com.google.android.material.snackbar.Snackbar

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}