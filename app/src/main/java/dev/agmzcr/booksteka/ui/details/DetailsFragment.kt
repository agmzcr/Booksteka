package dev.agmzcr.booksteka.ui.details

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.booksteka.R
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.data.model.Manga
import dev.agmzcr.booksteka.databinding.FragmentDetailsBinding
import dev.agmzcr.booksteka.util.getColorFromImage

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var genreAdapter: ListAdapterDetailsGenre
    private lateinit var authorAdapter: ListAdapterDetailsAuthor
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initRecyclerViews()
        detailsViewModel.result.observe(viewLifecycleOwner, {
            populateData(it)
        })
        detailsViewModel.fetchData()
        setupAddButton()
        return binding.root
    }

    private fun populateData(manga: Manga) {
        Glide.with(this)
            .load(manga.image_url)
            .into(binding.mangaImage)

        binding.apply {
            titleText.text = manga.title
            synopsisValue.text = manga.synopsis
            genreAdapter.submitList(manga.genres)
            authorAdapter.submitList(manga.authors)

            if (manga.volumes == null) {
                volumesValue.text = "N/A"
            } else {
                volumesValue.text = manga.volumes.toString()
            }

        }
        if (detailsViewModel.isMangaSaved(manga.manga_Id!!)) {
            isSaved = true
            binding.addButton.text = "Borrar manga"
        }
    }

    private fun initRecyclerViews() {
        genreAdapter = ListAdapterDetailsGenre()
        authorAdapter = ListAdapterDetailsAuthor()
        binding.apply {
            //recyclerViewGenre.layoutManager = LinearLayoutManager(context)
            recyclerViewGenre.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewGenre.adapter = genreAdapter
            recyclerViewAuthor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewAuthor.adapter = authorAdapter
        }
    }

    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            if (!isSaved) {
                detailsViewModel.insertMangaOnDb()
                binding.addButton.text = "Borrar manga"
            } else {
                detailsViewModel.result.value?.let { manga ->
                    detailsViewModel.deleteManga(manga.manga_Id!!)
                    view?.let {
                        Snackbar.make(it, "Manga ${manga.title} borrado", Snackbar.LENGTH_LONG).apply {
                            //collectionViewModel.undoDeletion(manga)
                        }.show()
                    }
                }
                binding.addButton.text = "Añadir a lista de deseos"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().navigateUp()
        }
        return true
    }

}