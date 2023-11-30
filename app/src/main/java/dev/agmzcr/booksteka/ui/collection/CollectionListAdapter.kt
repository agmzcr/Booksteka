package dev.agmzcr.booksteka.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.agmzcr.booksteka.data.entity.Books
import dev.agmzcr.booksteka.data.entity.BooksComplete
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.databinding.ListCollectionBinding
import dev.agmzcr.booksteka.databinding.ListReadingBinding
import java.util.*
import kotlin.collections.ArrayList


class CollectionListAdapter(
    private val clickListener: OnClickListener
    ): ListAdapter<MangaEntity, CollectionListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionListAdapter.ViewHolder {
        val binding = ListCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListCollectionBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = getItem(position)
                    if (book != null) {
                        clickListener.onBookClick(book)
                    }
                }
            }
        }
                fun bind(manga: MangaEntity) {
                    binding.apply {
                        titleValue.text = manga.title
                        Glide.with(itemView)
                            .load(manga.image_url)
                            .thumbnail()
                            .into(bookImage)
                    }
                }
            }

    interface OnClickListener {
        fun onBookClick(manga: MangaEntity)
    }

    class DiffCallback : DiffUtil.ItemCallback<MangaEntity>(){
        override fun areItemsTheSame(oldItem: MangaEntity, newItem: MangaEntity): Boolean {
            return oldItem.manga_Id == newItem.manga_Id
        }

        override fun areContentsTheSame(oldItem: MangaEntity, newItem: MangaEntity): Boolean {
            return oldItem == newItem
        }

    }
}