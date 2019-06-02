package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.feature.base.BaseIntent

sealed class HomeIntent : BaseIntent {


    companion object {
        const val INITIAL = 0
        const val START = 1
        const val RESULT = 2
        const val FINISH = 3
    }
    data class CommonIntent(val intentKey: Int) : HomeIntent()
    data class SelectLevelIntent(val levelId: Int) : HomeIntent()
    data class SelectAnswerIntent(val option: Int) : HomeIntent()


}
