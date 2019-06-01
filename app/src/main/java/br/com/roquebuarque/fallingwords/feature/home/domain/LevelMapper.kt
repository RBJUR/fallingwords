package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.feature.home.presentation.WordResult

object LevelMapper {

    operator fun invoke(levelId: Int)= when (levelId) {
            1 -> WordResult.SelectLevelResult.Success(15, 4)
            2 -> WordResult.SelectLevelResult.Success(10, 2)
            else -> WordResult.SelectLevelResult.Success(5, 1)
        }
}