package dev.agmzcr.booksteka.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.agmzcr.booksteka.data.entity.AuthorsEntity
import dev.agmzcr.booksteka.data.entity.GenreEntity
import dev.agmzcr.booksteka.data.entity.MangaWithGenre
import dev.agmzcr.booksteka.databinding.ListTextBinding

class ListAdapterDetailsAuthor : ListAdapter<AuthorsEntity,
        ListAdapterDetailsAuthor.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapterDetailsAuthor.ViewHolder {
        val binding = ListTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterDetailsAuthor.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListTextBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind (item: AuthorsEntity) {
                    binding.apply {
                        genreValue.text = item.name
                    }

                }
            }

    class DiffCallback : DiffUtil.ItemCallback<AuthorsEntity>() {
        override fun areItemsTheSame(oldItem: AuthorsEntity, newItem: AuthorsEntity): Boolean {
            return oldItem.author_Id == newItem.author_Id
        }

        override fun areContentsTheSame(oldItem: AuthorsEntity, newItem: AuthorsEntity): Boolean {
            return oldItem == newItem
        }

    }

}