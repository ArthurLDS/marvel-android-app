package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.presentation.character.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    viewModel { CharactersViewModel(get()) }
}