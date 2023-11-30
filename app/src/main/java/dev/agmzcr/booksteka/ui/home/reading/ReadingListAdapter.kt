package dev.agmzcr.booksteka.ui.home.reading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.agmzcr.booksteka.data.entity.BooksComplete
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.data.model.Manga
import dev.agmzcr.booksteka.databinding.ListReadingBinding


class ReadingListAdapter(private val clickListener: OnClickListener): ListAdapter<MangaEntity, ReadingListAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReadingListAdapter.ViewHolder {
        val binding = ListReadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReadingListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListReadingBinding) :
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

            binding.add1Btn.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val manga = getItem(position)
                    if (manga != null) {
                        clickListener.onOneMore(manga)
                    }
                }
            }
        }
                fun bind(manga: MangaEntity) {
                    binding.addToReadBtn.setOnClickListener {
                        val position = bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val book = getItem(position)
                            if (book != null) {
                                clickListener.onReadClick(book)
                            }
                        }
                    }

                    binding.apply {
                        Glide.with(itemView)
                            .load(manga.image_url)
                            .thumbnail()
                            .into(bookImage)
                        titleValue.text = manga.title
                        if (manga.volumes == null) {
                            volumesValue.text = "${manga.volumesRead} / N/A"
                            volumesProgress.visibility = View.GONE
                            volumesProgress.max = 0
                            volumesProgress.progress = 0
                        } else {
                            volumesValue.text = "${manga.volumesRead} / ${manga.volumes}"
                            volumesProgress.visibility = View.VISIBLE
                            volumesProgress.max = manga.volumes!!
                            volumesProgress.progress = manga.volumesRead!!
                        }
                    }
                }
            }

    interface OnClickListener {
        fun onReadClick(manga: MangaEntity)
        fun onOneMore(manga: MangaEntity)
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