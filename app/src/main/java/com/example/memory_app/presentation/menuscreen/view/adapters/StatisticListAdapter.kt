package com.example.memory_app.presentation.menuscreen.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_app.R
import com.example.memory_app.databinding.StatisticViewItemBinding
import com.example.memory_app.domain.repository.Statistic


class StatisticListAdapter() : ListAdapter<Statistic, StatisticListAdapter.LevelViewHolder>(DiffCallback) {

    inner class LevelViewHolder(private var binding: StatisticViewItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(statistic: Statistic) {
            binding.levelsCompletedText.text =  binding.root.context.resources
                .getString(R.string.levels_completed, statistic.levelsCompleted.toString())

            val score = statistic.averageScore.value
            if(score == 0.0) binding.averageScoreText.visibility = View.GONE
            else binding.averageScoreText.text = binding.root.context.resources
                .getString(R.string.average_score, score.toInt().toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LevelViewHolder(
            StatisticViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val statistic = getItem(position)
        holder.bind(statistic)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Statistic>() {
        override fun areItemsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
            return oldItem.levelsCompleted == newItem.levelsCompleted
        }

        override fun areContentsTheSame(oldItem: Statistic, newItem: Statistic): Boolean = true

    }
}