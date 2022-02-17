package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    viewModel { MainViewModel(get()) }
}