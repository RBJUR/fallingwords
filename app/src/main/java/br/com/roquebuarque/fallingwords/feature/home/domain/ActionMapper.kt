package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeActionV2
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult

object ActionMapper {
    operator fun invoke(homeAction: HomeActionV2.CommonAction) = when (homeAction.type) {
        HomeActionV2.INITIAL -> HomeResult.Success(HomeResult.START)
        HomeActionV2.ANSWER_RESULT -> HomeResult.Success(HomeResult.NEXT)
        HomeActionV2.NEXT -> HomeResult.Success(HomeResult.NEXT)
        HomeActionV2.GAME_RESULT -> HomeResult.Success(HomeResult.FINISH)
        HomeActionV2.FINISH -> HomeResult.Success(HomeResult.START)
        else -> HomeResult.InFlight
    }
}