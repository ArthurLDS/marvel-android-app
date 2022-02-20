package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.presentation.feature.characterdetail.CharacterDetailViewModel
import br.com.cwi.marvelapp.presentation.feature.characters.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
}