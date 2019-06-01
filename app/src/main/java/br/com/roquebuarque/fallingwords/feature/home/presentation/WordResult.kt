package br.com.roquebuarque.fallingwords.feature.home.presentation

import br.com.roquebuarque.fallingwords.data.WordResponse

sealed class WordResult {

    sealed class LoadResult : WordResult() {
        data class Success(val data: List<WordResponse>) : WordResult()
        data class Failure(val error: Throwable?) : WordResult()
        object InFlight : WordResult()
        object Start : WordResult()
    }

    sealed class SelectLevelResult : WordResult() {
        data class Success(val time: Int, val size: Int) : WordResult()
        data class Failure(val error: Throwable) : WordResult()
        object InFlight : WordResult()

    }

    sealed class SelectAnswerResult : WordResult() {
        data class Success(val option: Int) : WordResult()
        data class Failure(val error: Throwable) : WordResult()
        object InFlight : WordResult()

    }

    sealed class NextAnswerResult : WordResult() {
        object Success : WordResult()
        data class Failure(val error: Throwable) : WordResult()
        object InFlight : WordResult()

    }

    sealed class FinishResult : WordResult() {
        object Success : WordResult()
        data class Failure(val error: Throwable) : WordResult()
        object InFlight : WordResult()

    }

}