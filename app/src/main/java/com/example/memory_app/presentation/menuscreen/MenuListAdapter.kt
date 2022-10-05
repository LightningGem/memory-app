package com.example.memory_app.presentation.menuscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_app.data.levels.resources.LevelResourcesHolder
import com.example.memory_app.databinding.LevelViewItemBinding
import com.example.memory_app.domain.model.Difficulty

class MenuListAdapter(private val clickListener: LevelListener,
                      private val resources : LevelResourcesHolder) :
    ListAdapter<Pair<String, Difficulty>, MenuListAdapter.LevelViewHolder>(DiffCallback) {

    inner class LevelViewHolder(private var binding: LevelViewItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: LevelListener,
                 levelInfo: Pair<String, Difficulty>) {
            binding.clickListener = clickListener
            binding.levelInfo = levelInfo
            binding.levelIcon.setImageResource(resources.getLevelResources(levelInfo.first).imageUris.last())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LevelViewHolder(
            LevelViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val levelInfo = getItem(position)
        holder.bind(clickListener, levelInfo)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Pair<String, Difficulty>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Difficulty>,
                                     newItem: Pair<String, Difficulty>) =
            oldItem.first == newItem.first

        override fun areContentsTheSame(oldItem: Pair<String, Difficulty>,
                                        newItem: Pair<String, Difficulty>) = true

    }
}

class LevelListener(val clickListener: (levelName : String) -> Unit) {
    fun onClick(levelName : String) = clickListener(levelName)
}



