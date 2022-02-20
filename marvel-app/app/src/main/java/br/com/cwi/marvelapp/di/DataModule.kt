package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.data.mapper.CharacterItemMapper
import br.com.cwi.marvelapp.data.mapper.CharacterMapper
import br.com.cwi.marvelapp.data.repositoty.CharacterRepositoryImpl
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import org.koin.dsl.module

val dataModule = module {
    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }
    single { CharacterMapper() }
    single { CharacterItemMapper() }
}