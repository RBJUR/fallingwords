package br.com.roquebuarque.fallingwords.feature.home.presentation

import timber.log.Timber

object StateMapper {

    operator fun invoke(previousState: HomeState, result: HomeResult) =
        when (result) {
            is HomeResult.Success -> {
                when {
                    result.type == HomeResult.LOAD -> {
                        previousState.copy(
                            type = HomeState.LEVEL,
                            data = result.data
                        )
                    }
                    result.type == HomeResult.START -> previousState.copy(
                        type = HomeState.START
                    )
                    result.type == HomeResult.RUNNING_GAME -> previousState.copy(
                        type = HomeState.RUNNING,
                        time = result.time,
                        size = result.size,
                        data = previousState.data.take(previousState.data.size/result.size)
                    )
                    result.type == HomeResult.ANSWER_RESULT -> {
                        val random = (IntentKey.RIGHT..IntentKey.WRONG).random()
                        val isRight = result.selectedAnswer == random
                        previousState.copy(
                            type = if (previousState.index < previousState.data.size) HomeState.RESULT else HomeState.FINISH,
                            index = previousState.index + 1,
                            countWrong = if (!isRight || result.selectedAnswer == IntentKey.NO_ANSWER) previousState.countWrong + 1 else previousState.countWrong,
                            countRight = if (isRight && result.selectedAnswer != IntentKey.NO_ANSWER) previousState.countRight + 1 else previousState.countRight,
                            result = if (result.selectedAnswer != IntentKey.NO_ANSWER) isRight else false
                        )
                    }
                    result.type == HomeResult.NEXT -> previousState.copy(type = if (previousState.index < previousState.data.size) HomeState.RUNNING else HomeState.FINISH)
                    result.type == HomeResult.FINISH -> previousState.copy(
                        type = HomeState.START,
                        countRight = 0,
                        countWrong = 0,
                        index = 0
                    )
                    else -> previousState.copy(type = HomeState.INFLIGHT)
                }

            }
            is HomeResult.Failure -> {
                Timber.d("HomeResult: Failure")
                previousState.copy(
                    error = result.error
                )

            }

            is HomeResult.InFlight -> {
                Timber.d("HomeResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)
            }
        }
}