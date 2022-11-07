package com.example.memory_app.presentation.gamescreen.view

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.core.widget.TextViewCompat.setTextAppearance
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.memory_app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameDialogFragment : DialogFragment() {
    companion object {
        const val LOAD_FAIL = -2
        const val MENU_CLICKED = -1
        const val LOSE_DIALOG = 0
    }
    private val args: GameDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        this.isCancelable = args.result == MENU_CLICKED

        val dialog = MaterialAlertDialogBuilder(requireContext())

        when(args.result) {
            LOAD_FAIL -> dialog.setTitle(R.string.load_error_title)
                .setIcon(R.drawable.ic_internet_error)
                .setPositiveButton(R.string.lose_positive_text) { _, _ -> restartCurrentGame() }

            MENU_CLICKED -> dialog.setTitle(R.string.menu_clicked_title)
                .setPositiveButton(R.string.menu_clicked_positive_text) { _, _ -> restartCurrentGame() }

            LOSE_DIALOG -> dialog.setTitle(R.string.lose_title)
                .setPositiveButton(R.string.lose_positive_text) { _, _ -> restartCurrentGame() }

            else -> {
                val dialogTextLayout = LinearLayout(requireContext())
                dialogTextLayout.orientation = LinearLayout.VERTICAL
                val text_view = TextView(requireContext())
                with(text_view){
                    text = getString(R.string.result_score, args.result.toString())
                    setPadding(0, 45, 0, 0)
                    gravity = Gravity.CENTER_HORIZONTAL
                    setTextAppearance(this, R.style.MaterialAlertDialog_App_Body_Text);
                }
                dialogTextLayout.addView(text_view)

                dialog.setTitle(R.string.success_title)
                .setIcon(R.drawable.ic_success0)
                .setView(dialogTextLayout)
                .setPositiveButton(R.string.success_positive_text) { _, _ -> restartCurrentGame() }
            }
        }
        return dialog.setNegativeButton(R.string.return_to_menu_text) { _, _ -> navigateToMenu() }.create()
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