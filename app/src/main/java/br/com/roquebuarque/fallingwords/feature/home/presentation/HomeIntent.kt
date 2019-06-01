package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.feature.base.BaseState

sealed class HomeIntent : BaseState {

    object InitialIntent : HomeIntent()
    object StartIntent : HomeIntent()
    data class SelectLevelIntent(val levelId: Int) : HomeIntent()
    data class SelectAnswerIntent(val answerId: Int, val isRight: Boolean) : HomeIntent()


}
