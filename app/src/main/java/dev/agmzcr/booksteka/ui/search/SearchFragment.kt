package dev.agmzcr.booksteka.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.booksteka.R
import dev.agmzcr.booksteka.data.model.MangasResults
import dev.agmzcr.booksteka.databinding.FragmentSearchBinding
import dev.agmzcr.booksteka.ui.MainViewModel
import dev.agmzcr.booksteka.ui.home.HomeFragmentDirections
import dev.agmzcr.booksteka.util.EventObserver
import dev.agmzcr.booksteka.util.State

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchListAdapter.OnClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_search, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initRecyclerView()
        searchViewModel.getMangasList()
        initObserver()

        return binding.root
    }

    private fun initObserver() {
        searchViewModel.mangasLiveData.observe(viewLifecycleOwner, EventObserver { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    //adapter.currentList.clear()
                }
                is State.Success -> {
                    state.data.let {
                        adapter.submitList(it.results)
                        adapter.notifyDataSetChanged()
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is State.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        adapter = SearchListAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.recyclerView.adapter = adapter
    }

    override fun onMangaClick(manga: MangasResults) {
        val id = manga.mal_id
        val action = SearchFragmentDirections.actionSearchToDetails(
            mangaId = id,
            isFetchFromRemote = true
        )
        view?.findNavController()?.navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_buttom, menu)
        val searchItem = menu.findItem(R.id.search_Button)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getMangaListByQuery(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener {
            searchViewModel.getMangasList()
            false
        }
    }
}