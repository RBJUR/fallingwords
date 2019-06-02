package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult

object LevelMapper {

    operator fun invoke(levelId: Int) = when (levelId) {
        //TODO CREATE KEYS FOR LEVEL VALUE
        1 -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 15, size = 4)
        2 -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 10, size = 2)
        else -> HomeResult.Success(type = HomeResult.RUNNING_GAME, time = 5, size = 1)
    }
}