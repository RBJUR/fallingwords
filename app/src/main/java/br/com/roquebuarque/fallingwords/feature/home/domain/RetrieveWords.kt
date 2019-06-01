package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.data.Repository
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeAction
import br.com.roquebuarque.fallingwords.feature.home.presentation.WordResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RetrieveWords @Inject constructor(private val api: Repository) {

    fun transformerFromAction(): ObservableTransformer<HomeAction, WordResult> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(HomeAction.Start::class.java).compose(start()),
                    shared.ofType(HomeAction.Load::class.java).compose(loadWords()),
                    shared.ofType(HomeAction.SelectLevel::class.java).compose(selectLevel()),
                    shared.ofType(HomeAction.SelectAnswer::class.java).compose(selectAnswer()),
                    shared.ofType(HomeAction.Result::class.java).compose(result()),
                    shared.ofType(HomeAction.Finish::class.java).compose(finish())
                )
            }
        }
    }

    private fun start(): ObservableTransformer<HomeAction.Start, WordResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(WordResult.LoadResult.Start)
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.LoadResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.LoadResult.InFlight)
            }
        }

    private fun finish(): ObservableTransformer<HomeAction.Finish, WordResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(WordResult.FinishResult.Success)
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.FinishResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.FinishResult.InFlight)
            }
        }

    private fun result(): ObservableTransformer<HomeAction.Result, WordResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it)
                    .map { WordResult.NextAnswerResult.Success }
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.NextAnswerResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.NextAnswerResult.InFlight)
            }
        }

    private fun selectAnswer(): ObservableTransformer<HomeAction.SelectAnswer, WordResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it)
                    .map { item -> WordResult.SelectAnswerResult.Success(item.option) }
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.SelectAnswerResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.SelectAnswerResult.InFlight)
            }
        }

    private fun loadWords(): ObservableTransformer<HomeAction.Load, WordResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                api.fetchWords()
                    .map { selectableItems -> WordResult.LoadResult.Success(selectableItems) }
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.LoadResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.LoadResult.InFlight)
            }
        }
    }


    private fun selectLevel(): ObservableTransformer<HomeAction.SelectLevel, WordResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it.levelId)
                    .map { levelKey -> LevelMapper(levelKey) }
                    .cast(WordResult::class.java)
                    .onErrorReturn { t -> WordResult.SelectLevelResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(WordResult.SelectLevelResult.InFlight)

            }

        }
}