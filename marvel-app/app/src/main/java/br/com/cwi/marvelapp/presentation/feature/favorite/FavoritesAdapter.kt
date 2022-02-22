package br.com.cwi.marvelapp.presentation.feature.favorite

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ItemCharacterBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.presentation.extension.inflate
import br.com.cwi.marvelapp.presentation.extension.loadImage
import br.com.cwi.marvelapp.presentation.feature.favorite.FavoritesAdapter.FavoritesViewHolder

class FavoritesAdapter(
    private var list: List<CharacterItem> = listOf(),
    private val onClickFavorite: (CharacterItem) -> Unit
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    fun addItems(newItems: List<CharacterItem>) {
        list = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(parent.inflate(R.layout.item_character, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    inner class FavoritesViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val ivCharacter = ItemCharacterBinding.bind(item).ivCharacter
        private val tvCharacterName = ItemCharacterBinding.bind(item).tvCharacterName
        private var ivFavorite = ItemCharacterBinding.bind(item).ivFavorite

        fun bind(character: CharacterItem) = with(character) {
            ivCharacter.loadImage(getUrlImage())
            tvCharacterName.text = name

            with(ivFavorite) {
                setOnClickListener {
                    onClickFavorite(character)
                    notifyDataSetChanged()
                }
                setImageDrawable(getIcon(character.isFavorite))
            }
        }

        private fun getIcon(isFavorite: Boolean) : Drawable? {
            val icon : Int = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            return ContextCompat.getDrawable(itemView.context, icon)
        }
    }
}