package br.com.cwi.marvelapp.presentation.character

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.cwi.marvelapp.R
import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.databinding.ItemCharacterBinding
import br.com.cwi.marvelapp.presentation.extension.inflate
import com.bumptech.glide.Glide

class CharacterAdapter(private var list: List<Character> = listOf()) : Adapter<CharacterViewHolder>() {

    fun addItems(newItems: List<Character>) {
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
}

class CharacterViewHolder(item: View) : ViewHolder(item) {

    private val ivCharacter = ItemCharacterBinding.bind(item).ivCharacter
    private val tvCharacterName = ItemCharacterBinding.bind(item).tvCharacterName

    fun bind(character: Character) {
        Glide.with(ivCharacter.context)
            .load("${character.thumbnail?.path}.${character.thumbnail?.extension}")
            .centerCrop()
            .into(ivCharacter)

        tvCharacterName.text = character.name
    }
}