package dev.agmzcr.booksteka.ui.collection

import android.icu.lang.UCharacter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.booksteka.R
import dev.agmzcr.booksteka.data.entity.BooksComplete
import dev.agmzcr.booksteka.data.entity.MangaEntity
import dev.agmzcr.booksteka.databinding.FragmentCollectionBinding
import dev.agmzcr.booksteka.ui.MainViewModel
import dev.agmzcr.booksteka.ui.home.HomeFragmentDirections

@AndroidEntryPoint
class CollectionFragment : Fragment(), CollectionListAdapter.OnClickListener {

    private lateinit var binding: FragmentCollectionBinding
    private val collectionViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: CollectionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_collection, container, false
        )
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initRecylerView()
        initTouchHelper()
        collectionViewModel.getCollection()
        collectionViewModel.collectionLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        return binding.root
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
                collectionViewModel.deleteManga(manga.manga_Id!!)
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

    private fun initRecylerView() {
        adapter = CollectionListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.collection_buttoms, menu)

        val searchItem = menu.findItem(R.id.search_Button)
        val searchView = searchItem.actionView as SearchView
        val filterItem = menu.findItem(R.id.filter_Button)
        val filterView = filterItem.actionView as Spinner
        val filtersList = resources.getStringArray(R.array.Filters)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filtersList)
        filterView.adapter = spinnerAdapter
        filterView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        val filterList = adapter.currentList.sortedBy { it.title }
                        //Log.i("LISTCURRENT", filterList.toString())
                        adapter.submitList(filterList)
                    }
                    1 -> {
                        val filterList = adapter.currentList.sortedBy { it.score }
                        //Log.i("LISTCURRENT", filterList.toString())
                        adapter.submitList(filterList)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        /*filterItem.setOnMenuItemClickListener {

            val filterList = adapter.currentList.sortedBy { it.title }
            Log.i("LISTCURRENT", filterList.toString())
            adapter.submitList(filterList)
            adapter.notifyDataSetChanged()
            return@setOnMenuItemClickListener false
        }*/
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                collectionViewModel.getCollectionBySearch(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //searchViewModel.getMangaListByQuery(newText!!)
                return false
            }
        })
        searchView.setOnCloseListener {
            collectionViewModel.getCollection()
            false
        }
    }

    override fun onBookClick(manga: MangaEntity) {
        val action = CollectionFragmentDirections.actionCollectionToDetails(
            mangaId = manga.manga_Id!!,
            isFetchFromRemote = false
        )
        view?.findNavController()?.navigate(action)
    }
}