package br.com.cwi.marvelapp.presentation.feature.characterdetail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ItemComicsBinding
import br.com.cwi.marvelapp.domain.model.Item
import br.com.cwi.marvelapp.presentation.extension.inflate
import br.com.cwi.marvelapp.presentation.extension.loadImage
import br.com.cwi.marvelapp.presentation.feature.characterdetail.SerieAdapter.CharacterComicViewHolder

class SerieAdapter(var list: List<Item> = listOf()): Adapter<CharacterComicViewHolder>() {

    fun updateItems(newItems: List<Item>) {
        list = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterComicViewHolder(parent.inflate(R.layout.item_comics))

    override fun onBindViewHolder(holder: CharacterComicViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class CharacterComicViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ivItem = ItemComicsBinding.bind(view).ivComic
        private val tvItemName = ItemComicsBinding.bind(view).tvComicName

        fun bind(item: Item) {
            ivItem.loadImage(item.resourceURI)
            tvItemName.text = item.name
        }
    }

}