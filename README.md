# Marvel APP Android

[![Build](https://img.shields.io/static/v1?label=build&message=passing&color=green)]()
[![Kotlin](https://img.shields.io/static/v1?label=kotlin&message=powered&color=00AFF0)]()
[![Koin](https://img.shields.io/static/v1?label=koin&message=2.1.5&color=F68212)]()
[![Glide](https://img.shields.io/static/v1?label=glide&message=4.11.0&color=00C4CC)]()
[![Mockk](https://img.shields.io/static/v1?label=mockk&message=1.10.0&color=9F55FF)]()
[![Badge](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)]()

## Features

- Listagem de personagens, com lista infinita, barra de buscas, pull-to-refresh e seleção de tipo da listagem.
- Listagem de detalhes do personagem, junto com seus Comics e Series.
- Armazenamento local e gerenciamento de personagens favoritos

## Features Tecnicas
- Armazenamento de dados locais usando `Room`.
- Acesso de elementos de View usando `View Binding`.
- Navegação entre telas usando `Android Navigation`.
- Uso de boas práticas do Architecture Components do Android em geral.
- Implementação de injeção de dependências usando `Koin`.
- Código escrito buscando manter as melhores práticas de Clean Code.
- Cobertura de teste unitário nas View Models e Repositorios, usando `Mockk`.


## Arquitetura e Padrões de projeto

- A arquitetura do APP foi construita baseada em alguns conceitos do **Clean Architecture**, porém não implementa todos seus detalhes. A arquitetura implementada consiste em 3 camadas, data (infraesturura, comunicação com APIs, etc.), domain (regras de negocio e definição de contatos) e presentation (parte de visualização do aplicativo).

  <image src="https://user-images.githubusercontent.com/18702590/155230117-69877898-f2e0-4216-a760-c0bafa1f0281.png" width="500"/>

- O aplicativo usa **MVVM** como Pattern para camada de apresentação, conforme recomendação do próprio Google.

## Tecnologias

As seguintes bibliotecas e frameworks foram usadas na construção do projeto:
- [Navigation](https://developer.android.com/room/androidx?authuser=1)
- [Koin](https://github.com/InsertKoinIO/koin)
- [Kotlin Coroutines](https://developer.android.com/courses/pathways/android-coroutines)
- [Glide](https://github.com/bumptech/glide)
- [Retrofit](https://github.com/square/retrofit)
- [Mockk](https://github.com/mockk/mockk)
- [AndroidX](https://developer.android.com/jetpack/androidx?authuser=1)
- [Room](https://developer.android.com/room/androidx?authuser=1)

## 📱 Preview

https://user-images.githubusercontent.com/18702590/155229538-ac192fcd-e751-49b3-aa2a-3fc5b10ee817.mp4
