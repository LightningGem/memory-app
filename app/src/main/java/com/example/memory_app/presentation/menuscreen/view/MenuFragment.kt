package com.example.memory_app.presentation.menuscreen.view

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memory_app.R
import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.databinding.FragmentMenuBinding
import com.example.memory_app.presentation.menuscreen.view.adapters.LevelsInfoListAdapter
import com.example.memory_app.presentation.menuscreen.view.adapters.StatisticListAdapter
import com.example.memory_app.presentation.menuscreen.viewmodel.MenuViewModel
import com.example.memory_app.presentation.menuscreen.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var resources : LevelsResourcesHolder

    private val menuViewModel: MenuViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val levelsInfoListAdapter = LevelsInfoListAdapter(
            { levelName -> findNavController()
                .navigate(MenuFragmentDirections.actionLoadGame(levelName))},
            resources,
            requireContext()
        )
        val statisticListAdapter = StatisticListAdapter(requireContext())

        val concatAdapter = ConcatAdapter(statisticListAdapter, levelsInfoListAdapter)

        binding.levelsRecycleView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            menuViewModel.levelsInfoState.collect {
                when(it) {
                    is UiState.Loading -> {

                    }
                    is UiState.Error -> {
                    Toast.makeText(requireContext(), "${it.throwable}", Toast.LENGTH_SHORT).show()
                        levelsInfoListAdapter.submitList(listOf())
                    }
                    is UiState.Success -> { levelsInfoListAdapter.submitList(it.levelsInfo) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            menuViewModel.statistic.collect {
                if (it != null) statisticListAdapter.submitList(listOf(it))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_options, menu)
        menu.findItem(R.id.sourcetype_item).actionView.setOnClickListener {
            menuViewModel.changeLevelsSource()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            menuViewModel.isRemoteSource.collect { isRemote ->
                if(isRemote) {
                    menu.findItem(R.id.retry_item).isVisible = true
                    menu.findItem(R.id.sourcetype_item).actionView
                        .findViewById<TextView>(R.id.sourse_type)
                        .text = getString(R.string.local)
                } else {
                    menu.findItem(R.id.retry_item).isVisible = false
                    menu.findItem(R.id.sourcetype_item).actionView
                        .findViewById<TextView>(R.id.sourse_type)
                        .text = getString(R.string.remote)
                }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.retry_item ->  {
                menuViewModel.retry()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}