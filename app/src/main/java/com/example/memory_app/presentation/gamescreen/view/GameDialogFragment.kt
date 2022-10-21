package com.example.memory_app.presentation.gamescreen.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.memory_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDialogFragment : DialogFragment() {
    companion object {
        const val LOAD_FAIL = -2
        const val MENU_CLICKED = -1
        const val FAILURE_DIALOG = 0
    }
    private val args: GameDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        this.isCancelable = args.result == MENU_CLICKED

        val dialog = AlertDialog.Builder(requireContext())

        when(args.result) {
            LOAD_FAIL -> dialog.setTitle("Cards loading failed")
                .setIcon(R.drawable.ic_internet_error)
                .setPositiveButton("Try load again") { _, _ -> restartCurrentGame() }

            MENU_CLICKED -> dialog.setTitle("Leaving?")
                .setPositiveButton("Restart") { _, _ -> restartCurrentGame() }

            FAILURE_DIALOG -> dialog.setTitle("Better try next time")
                .setPositiveButton("Try again") { _, _ -> restartCurrentGame() }

            else -> { dialog.setTitle("Well done!")
                    .setMessage("Score : ${args.result}")
                    .setPositiveButton("Play again") { _, _ -> restartCurrentGame() }
            }
        }
        return dialog.setNegativeButton("Back to menu") { _, _ -> navigateToMenu() }.create()
    }

    private fun restartCurrentGame() {
        findNavController().navigate(
            GameDialogFragmentDirections.actionRestartCurentGame(args.levelName)
        )
    }

    private fun navigateToMenu() {
        findNavController().navigate(GameDialogFragmentDirections.actionBackToMenu())
    }
}