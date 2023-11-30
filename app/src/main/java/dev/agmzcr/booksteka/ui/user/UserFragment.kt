package dev.agmzcr.booksteka.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.booksteka.R
import dev.agmzcr.booksteka.databinding.FragmentUserBinding
import dev.agmzcr.booksteka.ui.MainViewModel

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val userViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        userViewModel.readAllData.observe(viewLifecycleOwner, {
            binding.apply {
                libroTitle.text = it[0].manga.title
                authorTitle.text = it[0].genre[0].name
                genreTitle.text = it[0].genre[1].name
                publisherTitle.text = it[0].genre[2].name
            }
        })

        userViewModel.readAllData2.observe(viewLifecycleOwner, {
            binding.apply {
                a1Title.text = it[0].author[0].name
                a2Title.text = it[0].author[1].name
            }
        })

        userViewModel.getManga(1)
        return binding.root
    }
}