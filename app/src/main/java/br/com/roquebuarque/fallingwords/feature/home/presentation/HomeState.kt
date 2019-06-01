package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.data.WordResponse
import br.com.roquebuarque.fallingwords.feature.base.BaseState

data class HomeState(
    val type: Int,
    val data: List<WordResponse>,
    val index: Int,
    val result: Boolean,
    val countRight: Int,
    val countWrong: Int,
    val time: Int,
    val size: Int,
    val error: Throwable? = null
) : BaseState {
    companion object {
        const val IDLE = 0
        const val START = 1
        const val LEVEL = 2
        const val RUNING = 3
        const val ANSWER = 4
        const val RESULT = 5
        const val NEXT = 6
        const val FINISH = 7

        fun idle(): HomeState {
            return HomeState(
                type = IDLE,
                data = emptyList(),
                index = 0,
                result = false,
                countRight = 0,
                countWrong = 0,
                time = 0,
                size = 0
            )
        }
    }
}
