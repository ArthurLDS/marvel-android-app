package br.com.cwi.marvelapp.presentation.feature.characters

import android.view.View
import android.view.ViewGroup
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
    private val onClickItem: (Long) -> Unit
) : Adapter<CharacterViewHolder>() {

    fun addItems(newItems: List<CharacterItem>) {
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

        fun bind(character: CharacterItem) {

            ivCharacter.loadImage("${character.thumbnail?.path}.${character.thumbnail?.extension}")

            tvCharacterName.text = character.name

            itemView.setOnClickListener {
                onClickItem(character.id)
            }
        }
    }
}

