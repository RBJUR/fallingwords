package br.com.roquebuarque.fallingwords.feature.home.presentation

import timber.log.Timber

object StateMapper {

    operator fun invoke(previousState: HomeState, result: WordResult) =
        when (result) {
            is WordResult.LoadResult.Success -> {
                Timber.d("WordResult.LoadResult: Success")

                previousState.copy(
                    type = HomeState.LEVEL,
                    data = result.data
                )
            }
            is WordResult.LoadResult.Failure -> {
                Timber.d("WordResult.LoadResult: Failure")
                previousState.copy(
                    error = IllegalArgumentException("something went wrong")
                )

            }

            WordResult.LoadResult.InFlight -> {
                Timber.d("WordResult.LoadResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)
            }
            WordResult.LoadResult.Start -> {
                Timber.d("WordResult.LoadResult: Start")
                previousState.copy(
                    type = HomeState.START
                )
            }

            is WordResult.SelectLevelResult.Success -> {
                Timber.d("WordResult.SelectLevelResult: Success")
                previousState.copy(
                    type = HomeState.RUNNING,
                    time = result.time,
                    size = result.size
                )

            }

            is WordResult.SelectLevelResult.Failure -> {
                Timber.d("WordResult.SelectLevelResult: Failure")
                previousState.copy(
                    type = HomeState.IDLE,
                    error = IllegalArgumentException("something went wrong")
                )
            }

            WordResult.SelectLevelResult.InFlight -> {
                Timber.d("WordResult.SelectLevelResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)

            }
            is WordResult.SelectAnswerResult.Success -> {
                Timber.d("WordResult.SelectAnswerResult: Success")
                val isRight = (result.option == 1) == previousState.data[previousState.index].isRight
                Timber.d("Answer Result $isRight")

                previousState.copy(
                    type = if (previousState.index < previousState.data.size) HomeState.RESULT else HomeState.FINISH,
                    index = previousState.index + 1,
                    countWrong = if (!isRight) previousState.countWrong + 1 else previousState.countWrong,
                    countRight = if (isRight) previousState.countRight + 1 else previousState.countRight,
                    result = isRight
                )
            }
            is WordResult.SelectAnswerResult.Failure -> {
                Timber.d("WordResult.SelectAnswerResult: Failure")
                previousState.copy(
                    error = IllegalArgumentException("something went wrong")
                )
            }
            WordResult.SelectAnswerResult.InFlight -> {
                Timber.d("WordResult.SelectAnswerResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)
            }

            WordResult.NextAnswerResult.Success -> {
                Timber.d("WordResult.NextAnswerResult: Success")
                previousState.copy(type = if (previousState.index < previousState.data.size) HomeState.RUNNING else HomeState.FINISH)
            }
            is WordResult.NextAnswerResult.Failure -> {
                Timber.d("WordResult.NextAnswerResult: Failure")
                previousState.copy(
                    error = IllegalArgumentException("something went wrong")
                )
            }
            WordResult.NextAnswerResult.InFlight -> {
                Timber.d("WordResult.NextAnswerResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)
            }

            //else -> throw IllegalArgumentException("unknown result")

            WordResult.FinishResult.Success -> {
                Timber.d("WordResult.FinishResult: Success")
                previousState.copy(
                    type = HomeState.START,
                    countRight = 0,
                    countWrong = 0,
                    index = 0
                )
            }
            is WordResult.FinishResult.Failure -> {
                Timber.d("WordResult.FinishResult: Failure")
                previousState.copy(
                    error = IllegalArgumentException("something went wrong")
                )
            }
            WordResult.FinishResult.InFlight -> {
                Timber.d("WordResult.FinishResult: InFlight")
                previousState.copy(type = HomeState.INFLIGHT)
            }

        }

}