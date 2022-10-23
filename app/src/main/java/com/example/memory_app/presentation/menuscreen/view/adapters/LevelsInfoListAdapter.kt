package com.example.memory_app.presentation.menuscreen.view.adapters

import android.content.ContentResolver
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.memory_app.R
import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.databinding.LevelViewItemBinding
import com.example.memory_app.domain.model.Difficulty
import java.util.*


class LevelsInfoListAdapter(private val onClick: (String) -> Unit,
                            private val levelResources : LevelsResourcesHolder) :
    ListAdapter<Pair<String, Difficulty>, LevelsInfoListAdapter.LevelViewHolder>(DiffCallback) {

    inner class LevelViewHolder(private var binding: LevelViewItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(levelInfo: Pair<String, Difficulty>) {
            binding.root.setOnClickListener { onClick(levelInfo.first) }
            binding.levelNameText.text = levelInfo.first
            binding.difficultyText.text = levelInfo.second.name.lowercase(Locale.ROOT)
            binding.cardsInRowText.text = binding.root.context.resources
                    .getString(R.string.cards_in_row, levelInfo.second.cardsInRow.toString())
            binding.numberOfCardsText.text = binding.root.context.resources
                    .getString(R.string.number_of_cards, levelInfo.second.NumberOfCards.toString())


            val imageUri = levelResources.getLevelResources(levelInfo.first).levelIconImageUri
            val isLocalResource = imageUri.toString().startsWith(ContentResolver.SCHEME_ANDROID_RESOURCE)
            val options = if (isLocalResource) RequestOptions()
            else RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.loading_animation)

            Glide.with(binding.root)
                .load(imageUri)
                .apply(options)
                .into(binding.levelIcon)
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
        holder.bind(levelInfo)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Pair<String, Difficulty>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Difficulty>,
                                     newItem: Pair<String, Difficulty>) =
            oldItem.first == newItem.first

        override fun areContentsTheSame(oldItem: Pair<String, Difficulty>,
                                        newItem: Pair<String, Difficulty>) =
            oldItem.second == newItem.second

    }
}

