package dev.agmzcr.booksteka.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.agmzcr.booksteka.data.model.MangasResults
import dev.agmzcr.booksteka.databinding.ListSearchBinding

class SearchListAdapter(private val clickListener: OnClickListener): ListAdapter<MangasResults, SearchListAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.ViewHolder {
       val binding = ListSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListSearchBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val manga = getItem(position)
                    if (manga != null) {
                        clickListener.onMangaClick(manga)
                    }
                }
            }
        }

        fun bind(item: MangasResults) {
                    Glide.with(itemView)
                        .load(item.image_url)
                        .thumbnail()
                        .into(binding.mangaImage)

                    binding.apply {
                        titleText.text = item.title
                        scoreText.text = item.score.toString()
                    }
                }
            }

    interface OnClickListener {
        fun onMangaClick(manga: MangasResults)
    }

    class DiffCallback : DiffUtil.ItemCallback<MangasResults>() {
        override fun areItemsTheSame(oldItem: MangasResults, newItem: MangasResults): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: MangasResults, newItem: MangasResults): Boolean {
           return oldItem == newItem
        }

    }

}