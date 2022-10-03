package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.repository.GameRepository
import kotlinx.coroutines.flow.map

class LoadLevelsInfoUseCase (private val repository: GameRepository) {
    operator fun invoke() = repository.getAllLevels().map { levels ->
        levels.map { level ->
            Triple(level.name, level.difficulty, level.faceDownImageId)
        }
    }
}