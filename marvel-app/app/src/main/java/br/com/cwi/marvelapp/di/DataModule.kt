package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.data.repositoty.CharacterRepositoryImpl
import br.com.cwi.marvelapp.domain.CharacterRepository
import org.koin.dsl.module

val dataModule = module {
    factory<CharacterRepository> { CharacterRepositoryImpl(get()) }
}