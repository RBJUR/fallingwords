package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult
import br.com.roquebuarque.fallingwords.feature.home.presentation.IntentKey

object LevelMapper {

    operator fun invoke(levelId: Int) = when (levelId) {
        IntentKey.EASY -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 16, size = 4)
        IntentKey.MEDIUM -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 11, size = 2)
        else -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 6, size = 1)
    }
}