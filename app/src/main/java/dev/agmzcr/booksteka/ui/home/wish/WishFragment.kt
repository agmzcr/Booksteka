package dev.agmzcr.booksteka.ui.home.wish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.booksteka.R
import dev.agmzcr.booksteka.data.entity.BooksComplete
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.databinding.FragmentWishBinding
import dev.agmzcr.booksteka.ui.MainViewModel
import dev.agmzcr.booksteka.ui.home.HomeFragmentDirections
import dev.agmzcr.booksteka.ui.home.reading.ReadingListAdapter

@AndroidEntryPoint
class WishFragment : Fragment(), WishListAdapter.OnClickListener {

    private lateinit var binding: FragmentWishBinding
    private val wishViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: WishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_wish, container, false
        )
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initRecylerView()
        initTouchHelper()

        wishViewModel.wishingData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        wishViewModel.getWishAndReadingList()
    }

    private fun initRecylerView() {
        adapter = WishListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun initTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val manga = adapter.currentList[position]
                wishViewModel.deleteManga(manga.manga_Id!!)
                view?.let {
                    Snackbar.make(it, "Manga ${manga.title} borrado", Snackbar.LENGTH_LONG).apply {
                        //collectionViewModel.undoDeletion(manga)
                    }.show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    override fun onReadClick(manga: MangaEntity) {
        Toast.makeText(context, "Libro: ${manga.title} movido a leidos", Toast.LENGTH_SHORT).show()
        /*manga.manga_Id?.let { wishViewModel.updateToRead(it) }
        adapter.notifyDataSetChanged()*/
    }

    override fun onReadingClick(manga: MangaEntity) {
        Toast.makeText(context, "Libro: ${manga.title} movido a leyendo", Toast.LENGTH_SHORT).show()
        manga.manga_Id?.let { wishViewModel.updateToReading(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onBookClick(manga: MangaEntity) {
        val action = HomeFragmentDirections.actionWishToDetails(
            mangaId = manga.manga_Id!!,
            isFetchFromRemote = false
        )
        view?.findNavController()?.navigate(action)
    }
}