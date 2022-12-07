package com.example.memory_app.domain.entities

import com.example.memory_app.domain.model.Difficulty

data class Level(val name : String,
                 val difficulty: Difficulty,
                 val CardIds : List<Int>)
