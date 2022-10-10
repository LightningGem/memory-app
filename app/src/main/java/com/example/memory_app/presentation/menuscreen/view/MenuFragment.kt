package com.example.memory_app.presentation.menuscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.databinding.FragmentMenuBinding
import com.example.memory_app.presentation.menuscreen.view.adapters.LevelsInfoListAdapter
import com.example.memory_app.presentation.menuscreen.view.adapters.StatisticListAdapter
import com.example.memory_app.presentation.menuscreen.viewmodel.MenuViewModel
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
            menuViewModel.levelsInfo.collect {
                if (it != null) levelsInfoListAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            menuViewModel.statistic.collect {
                if (it != null) statisticListAdapter.submitList(listOf(it))
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}