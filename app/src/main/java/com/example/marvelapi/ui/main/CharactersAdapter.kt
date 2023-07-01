package com.example.marvelapi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapi.databinding.AdapterCharactersBinding
import com.example.marvelapi.domain.model.Characters

class CharactersAdapter(
    private val mInvoke: (Characters) -> Unit
) :
    ListAdapter<Characters, CharactersAdapter.MenuViewHolder>(Characters.DIFF_CALLBACK) {

    inner class MenuViewHolder(private val binding: AdapterCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Characters) {
            binding.apply {
                val urlImage = "${item.thumbnail?.path}.${item.thumbnail?.extension}"
                Glide.with(aivImage)
                    .load(urlImage)
                    .into(aivImage)
                mtvTitle.text = item.name
                mtvDescription.text = if (item.description?.isEmpty() == true) "No description yet" else item.description
                root.setOnClickListener {
                    mInvoke.invoke(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersAdapter.MenuViewHolder {
        return MenuViewHolder(
            AdapterCharactersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersAdapter.MenuViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}