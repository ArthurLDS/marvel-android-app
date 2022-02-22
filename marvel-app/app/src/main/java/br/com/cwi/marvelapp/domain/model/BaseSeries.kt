package br.com.cwi.marvelapp.domain.model

class BaseSeries(
    val available: String = "",
    val collectionURI: String = "",
    val items: List<Item>? = null,
    val returned: String = ""
)