package com.example.memory_app.presentation.gamescreen.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.memory_app.presentation.gamescreen.viewmodel.DialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.meta.When

@AndroidEntryPoint
class GameDialogFragment : DialogFragment() {
    private val viewModel: DialogViewModel by viewModels()
    companion object {
        const val MENU_CLICKED = -1
        const val FAILURE_DIALOG = 0
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = viewModel.result == MENU_CLICKED

        val dialog = AlertDialog.Builder(requireContext())

        when(viewModel.result){
            MENU_CLICKED -> dialog.setTitle("Leaving?").setPositiveButton("Restart") { _, _ ->
                    findNavController().navigate(
                        GameDialogFragmentDirections.actionRestartCurentGame(
                            viewModel.levelName!!))
                }

            FAILURE_DIALOG -> dialog.setTitle("Better try next time") .setPositiveButton("Try again") { _, _ ->
                findNavController().navigate(
                    GameDialogFragmentDirections.actionRestartCurentGame(
                        viewModel.levelName!!))
            }

            else -> {
                dialog.setTitle("Well done!").setMessage("Score : ${viewModel.result}").setPositiveButton("Play again") { _, _ ->
                    findNavController().navigate(
                        GameDialogFragmentDirections.actionRestartCurentGame(
                            viewModel.levelName!!))
                }
            }
        }
        return dialog
            .setNegativeButton("Back to menu") { _, _ ->
                findNavController().navigate(
                    GameDialogFragmentDirections.actionBackToMenu())
            }

            .create()
    }
}