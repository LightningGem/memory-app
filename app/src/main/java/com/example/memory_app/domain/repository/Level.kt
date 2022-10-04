package com.example.memory_app.domain.repository

import com.example.memory_app.domain.model.Difficulty

data class Level(val name : String,
                 val difficulty: Difficulty,
                 val CardIds : List<Int>)
