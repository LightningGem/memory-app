package com.example.memory_app.presentation.gamescreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.memory_app.data.levels.resources.Resources
import com.example.memory_app.databinding.CardViewItemBinding
import com.example.memory_app.domain.model.Card
import com.example.memory_app.presentation.gamescreen.view.CardsAdapter.Companion.marginSize
import kotlin.math.min


class CardsAdapter(private val onClick: (Int) -> Unit,
                   private val levelResources : Resources,
                   private val numberOfColumns : Int,
                   private val numberOfRows : Int,
                   private val board : List<Card>,
                   private val context : Context) :
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

            val cardParam = binding.cardView.layoutParams as ViewGroup.MarginLayoutParams
            cardParam.height = cardSideLength
            cardParam.width = cardSideLength
            cardParam.setMargins(marginSize, marginTop + marginSize, marginSize, marginSize)

            if(card.isFaceUp) Glide.with(context)
                .load(levelResources.cardImagesUris[card.identifier])
                .into(binding.cardImage)
             else Glide.with(context).load(levelResources.faceOffImageUri).into(binding.cardImage)


            if(card.isMatched) {
                binding.cardImage.imageAlpha = imageAlpha
            } else {
                binding.root.setOnClickListener {onClick(position)}
            }
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
            CardViewItemBinding.inflate(layoutInflater, parent, false),
            cardSideLength - ( 2 * marginSize),
            marginTop)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val card = board[position]
        holder.bind(position, card)
    }

}