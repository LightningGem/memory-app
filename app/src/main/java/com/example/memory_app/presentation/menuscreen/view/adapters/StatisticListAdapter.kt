package com.example.memory_app.presentation.menuscreen.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_app.R
import com.example.memory_app.databinding.StatisticViewItemBinding
import com.example.memory_app.domain.entities.Statistic


class StatisticListAdapter() : ListAdapter<Statistic, StatisticListAdapter.LevelViewHolder>(DiffCallback) {

    inner class LevelViewHolder(private var binding: StatisticViewItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(statistic: Statistic) {

            val score = statistic.averageScore.value
            binding.averageScoreText.text = binding.root.context.resources
                .getString(R.string.average_score, String.format("%.1f", score).replace(",", "."))

            val res : Pair<Int, Int>;
            with(binding.root.context) {
                res = if(statistic.levelsCompleted >=  resources.getInteger(R.integer.hard))
                    Pair(R.drawable.ic_success3, getColor(R.color.difficulty2Color))

                else if(statistic.levelsCompleted >=  resources.getInteger(R.integer.medium))
                    Pair(R.drawable.ic_success2, getColor(R.color.difficulty1Color))

                else Pair(R.drawable.ic_success1,getColor(R.color.difficulty0Color))
            }
            binding.icon.setImageResource(res.first)
            binding.levelsCompletedText.text =  binding.root.context.resources
                .getString(R.string.levels_completed, statistic.levelsCompleted.toString())
            binding.levelsCompletedText.setTextColor(res.second)

            binding.root.setOnClickListener {
                if(binding.description.visibility == View.GONE) {
                    binding.description.visibility = View.VISIBLE
                } else binding.description.visibility = View.GONE
            }
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