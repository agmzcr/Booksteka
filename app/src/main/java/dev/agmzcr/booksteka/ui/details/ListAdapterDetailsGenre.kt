package dev.agmzcr.booksteka.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.agmzcr.booksteka.data.entity.GenreEntity
import dev.agmzcr.booksteka.data.entity.MangaWithGenre
import dev.agmzcr.booksteka.databinding.ListTextBinding

class ListAdapterDetailsGenre : ListAdapter<GenreEntity,
        ListAdapterDetailsGenre.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapterDetailsGenre.ViewHolder {
        val binding = ListTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterDetailsGenre.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListTextBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind (item: GenreEntity) {
                    binding.apply {
                        genreValue.text = item.name
                    }

                }
            }

    class DiffCallback : DiffUtil.ItemCallback<GenreEntity>() {
        override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
            return oldItem.genre_Id == newItem.genre_Id
        }

        override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
            return oldItem == newItem
        }

    }

}