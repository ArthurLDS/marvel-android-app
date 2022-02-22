package br.com.cwi.marvelapp.di

import br.com.cwi.marvelapp.data.mapper.CharacterItemLocalMapper
import br.com.cwi.marvelapp.data.mapper.CharacterItemMapper
import br.com.cwi.marvelapp.data.mapper.CharacterMapper
import br.com.cwi.marvelapp.data.mapper.ItemMapper
import br.com.cwi.marvelapp.data.repositoty.CharacterRepositoryImpl
import br.com.cwi.marvelapp.data.source.local.MarvelDatabase
import br.com.cwi.marvelapp.domain.repository.CharacterRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get(), get(), get()) }
    single { CharacterMapper() }
    single { CharacterItemMapper() }
    single { CharacterItemLocalMapper() }
    single { ItemMapper() }
    single { MarvelDatabase.create(androidApplication()).getItemDao() }
}