package br.com.cwi.marvelapp.presentation.feature.characters

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.databinding.ItemCharacterBinding
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.presentation.extension.inflate
import br.com.cwi.marvelapp.presentation.extension.loadImage
import br.com.cwi.marvelapp.presentation.feature.characters.CharacterAdapter.CharacterViewHolder

class CharacterAdapter(
    private var list: List<CharacterItem> = listOf(),
    private val onClickItem: (Long) -> Unit,
    private val onClickFavorite: (CharacterItem) -> Unit
) : Adapter<CharacterViewHolder>() {

    fun updateItems(newItems: List<CharacterItem>) {
        list = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(parent.inflate(R.layout.item_character, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    inner class CharacterViewHolder(item: View) : ViewHolder(item) {

        private val ivCharacter = ItemCharacterBinding.bind(item).ivCharacter
        private val tvCharacterName = ItemCharacterBinding.bind(item).tvCharacterName
        private var ivFavorite = ItemCharacterBinding.bind(item).ivFavorite

        fun bind(character: CharacterItem) = with(character) {
            ivCharacter.loadImage(getUrlImage())
            tvCharacterName.text = name

            itemView.setOnClickListener { onClickItem(id) }

            with(ivFavorite) {
                setOnClickListener {
                    onClickFavorite(character)
                    notifyDataSetChanged()
                    setImageDrawable(getIcon(character.isFavorite.not()))
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

