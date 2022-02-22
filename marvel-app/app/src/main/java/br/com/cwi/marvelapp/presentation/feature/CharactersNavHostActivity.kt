package br.com.cwi.marvelapp.presentation.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ActivityCharactersNavHostBinding

class CharactersNavHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersNavHostBinding

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment)
            .findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MarvelApp)
        binding = ActivityCharactersNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
        setUpBottomNavigation()
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }
    }

    private fun setUpBottomNavigation() {
        with(binding.bottomNavigation) {

            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                if (selectedItemId != item.itemId) when (item.itemId) {
                    R.id.bottom_characters -> navController.navigate(R.id.bottom_characters)
                    else -> navController.navigate(R.id.bottom_favorites)
                }
                return@setOnItemSelectedListener true
            }
        }
    }
}