package com.example.memory_app.presentation.gamescreen.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memory_app.R
import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.databinding.FragmentGameBinding
import com.example.memory_app.presentation.gamescreen.viewmodel.GameViewModel
import com.example.memory_app.presentation.gamescreen.viewmodel.Over
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.sqrt


@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var resources : LevelsResourcesHolder

    private val viewModel: GameViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog(GameDialogFragment.MENU_CLICKED)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var numberOfColumns = sqrt(viewModel.cardsBoard.value.size.toDouble()).toInt()
        var numberOfRows = viewModel.cardsBoard.value.size / numberOfColumns

        if (requireActivity()
                .resources
                .configuration
                .orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberOfRows += numberOfColumns
            numberOfColumns = numberOfRows - numberOfColumns
            numberOfRows -= numberOfColumns
        }

        binding.gridRecycleView.apply {
            layoutManager = GridLayoutManager(requireContext(), numberOfColumns)
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.cardsBoard.collect {
                    newBoard ->
                binding.gridRecycleView.adapter = CardsAdapter(
                    onClick = {position : Int ->
                        viewModel.onCardClicked(position) },
                    levelResources = resources.getLevelResources(viewModel.levelName!!),
                    numberOfColumns = numberOfColumns,
                    numberOfRows = numberOfRows,
                    board = newBoard
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.overEvent.collect() {
                    when(it) {
                        is Over.Failure -> { showDialog(GameDialogFragment.FAILURE_DIALOG) }
                        is Over.Success -> { showDialog(it.score.value.toInt()) }
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.game_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_item ->  {
                showDialog(GameDialogFragment.MENU_CLICKED)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.mismatchesLeft.collect {
                menu.findItem(R.id.mismatches_left_item)
                    .actionView
                    .findViewById<TextView>(R.id.mismatches_left)
                    .text = it.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(result : Int){
        findNavController().navigate(GameFragmentDirections
            .actionShowDialogFragment(viewModel.levelName, result))
    }
}