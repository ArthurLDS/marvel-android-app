package br.com.cwi.marvelapp.di

import org.koin.core.module.Module

val appModule : List<Module> = listOf(networkModule, dataModule, presentationModule)