package dev.agmzcr.booksteka.ui.home.wish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.databinding.ListWishBinding


class WishListAdapter(private val clickListener: OnClickListener): ListAdapter<MangaEntity, WishListAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishListAdapter.ViewHolder {
        val binding = ListWishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListWishBinding) :
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

            /*binding.addToReadBtn.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = getItem(position)
                    if (book != null) {
                        clickListener.onReadClick(book)
                    }
                }
            }*/

            binding.addToReadingBtn.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = getItem(position)
                    if (book != null) {
                        clickListener.onReadingClick(book)
                    }
                }
            }
        }
                fun bind(item: MangaEntity) {
                    binding.apply {
                        Glide.with(itemView)
                            .load(item.image_url)
                            .thumbnail()
                            .into(bookImage)
                        titleValue.text = item.title
                    }
                }
            }

    interface OnClickListener {
        fun onReadClick(book: MangaEntity)
        fun onReadingClick(book: MangaEntity)
        fun onBookClick(book: MangaEntity)
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