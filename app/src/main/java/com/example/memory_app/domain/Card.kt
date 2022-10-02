package com.example.memory_app.domain

data class Card (
                 val identifier : Int,
                 val isMatched : Boolean = false,
                 val isFaceUp : Boolean = false)