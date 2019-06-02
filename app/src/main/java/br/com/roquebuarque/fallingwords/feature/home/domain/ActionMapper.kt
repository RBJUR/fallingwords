package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeAction
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult

object ActionMapper {
    operator fun invoke(homeAction: HomeAction.CommonAction) = when (homeAction.type) {
        HomeAction.INITIAL -> HomeResult.Success(HomeResult.START)
        HomeAction.ANSWER_RESULT -> HomeResult.Success(HomeResult.NEXT)
        HomeAction.NEXT -> HomeResult.Success(HomeResult.NEXT)
        HomeAction.GAME_RESULT -> HomeResult.Success(HomeResult.FINISH)
        HomeAction.FINISH -> HomeResult.Success(HomeResult.START)
        else -> HomeResult.InFlight
    }
}