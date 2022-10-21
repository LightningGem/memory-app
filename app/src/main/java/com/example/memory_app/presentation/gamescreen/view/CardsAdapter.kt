package com.example.memory_app.presentation.gamescreen.view

import android.content.ContentResolver
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.memory_app.R
import com.example.memory_app.data.levels.resources.Resources
import com.example.memory_app.databinding.CardViewItemBinding
import com.example.memory_app.domain.model.Card
import kotlin.math.min


class CardsAdapter(private val onClick: (Int) -> Unit,
                   private val levelResources : Resources,
                   private val numberOfColumns : Int,
                   private val numberOfRows : Int,
                   private val board : List<Card>,
                   private val onImageLoadFailed: () -> Unit,
                   private val retrieveFromCache : Boolean,
                   private val gameFragment: GameFragment) :
    RecyclerView.Adapter<CardsAdapter.LevelViewHolder>() {
    companion object {
        const val marginSize : Int = 20
        const val imageAlpha : Int = 60
    }
    
    inner class LevelViewHolder(private var binding: CardViewItemBinding,
                                private val cardSideLength : Int,
                                private val marginTop: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position : Int, card: Card) {

            with(binding.cardView.layoutParams as ViewGroup.MarginLayoutParams) {
                height = cardSideLength
                width = cardSideLength
                setMargins(marginSize, marginTop + marginSize, marginSize, marginSize)
            }

            val requestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    if(!retrieveFromCache) {
                        Glide.with(gameFragment).pauseRequests()
                        onImageLoadFailed()
                    }
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    if(card.isMatched) binding.cardImage.imageAlpha = imageAlpha
                    else binding.root.setOnClickListener {onClick(position)}
                    return false
                }
            }

            fun setupImage(imageUri : Uri) {
                val isLocalResource = imageUri.toString().startsWith(ContentResolver.SCHEME_ANDROID_RESOURCE)
                val options = if (isLocalResource) RequestOptions()
                else RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.loading_animation)

                Glide.with(gameFragment)
                    .load(imageUri)
                    .listener(requestListener)
                    .apply(options)
                    .onlyRetrieveFromCache(retrieveFromCache)
                    .into(binding.cardImage)
            }

            if(card.isFaceUp) setupImage(levelResources.cardImagesUris[card.identifier])
            else setupImage(levelResources.faceOffImageUri)
        }
    }

    override fun getItemCount(): Int = board.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val cardWith = parent.width / numberOfColumns
        val cardHeight = parent.height / numberOfRows
        val cardSideLength = min(cardWith, cardHeight)
        var freeVerticalSpace = 0
        if (cardHeight > cardWith) freeVerticalSpace = parent.height - (cardSideLength * numberOfRows)
        val marginTop = freeVerticalSpace / (numberOfRows + 1)

        val layoutInflater = LayoutInflater.from(parent.context)
        return LevelViewHolder(
            binding = CardViewItemBinding.inflate(layoutInflater, parent, false),
            cardSideLength = cardSideLength - ( 2 * marginSize),
            marginTop = marginTop)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val card = board[position]
        holder.bind(position, card)
    }
}