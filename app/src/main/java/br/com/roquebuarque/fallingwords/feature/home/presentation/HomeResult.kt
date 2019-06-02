package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.data.WordResponse

sealed class HomeResult {

    companion object {
        const val START = 0
        const val LOAD = 1
        const val RUNNING_GAME = 2
        const val ANSWER_RESULT = 3
        const val NEXT = 4
        const val FINISH = 5
    }

    data class Success(
        val type: Int,
        var data: List<WordResponse> = listOf(),
        var time: Int = 0,
        var size: Int = 0,
        var selectedAnswer: Int = 0
    ) : HomeResult()

    data class Failure(val error: Throwable?) : HomeResult()
    object InFlight : HomeResult()
}