package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.feature.base.BaseState

sealed class HomeIntent : BaseState {

    object InitialIntent : HomeIntent()
    object StartIntent : HomeIntent()
    object ResultIntent : HomeIntent()
    object Finish : HomeIntent()
    data class SelectLevelIntent(val levelId: Int) : HomeIntent()
    data class SelectAnswerIntent(val option: Int) : HomeIntent()


}
