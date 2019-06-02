package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.data.Repository
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeActionV2
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RetrieveWords @Inject constructor(private val api: Repository) {

    fun transformerFromAction(): ObservableTransformer<HomeActionV2, HomeResult> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(HomeActionV2.CommonAction::class.java).compose(loadActions()),
                    shared.ofType(HomeActionV2.Load::class.java).compose(loadWords()),
                    shared.ofType(HomeActionV2.SelectLevel::class.java).compose(selectLevel()),
                    shared.ofType(HomeActionV2.SelectAnswer::class.java).compose(selectAnswer())
                    )
            }
        }
    }

    private fun loadActions(): ObservableTransformer<HomeActionV2.CommonAction, HomeResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it)
                    .map { item -> ActionMapper(item) }
                    .onErrorReturn { t -> HomeResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(HomeResult.InFlight)
            }
        }



    private fun selectAnswer(): ObservableTransformer<HomeActionV2.SelectAnswer, HomeResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it)
                    .map { item -> HomeResult.Success(type = HomeResult.ANSWER_RESULT, selectedAnswer = item.option) }
                    .cast(HomeResult::class.java)
                    .onErrorReturn { t -> HomeResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(HomeResult.InFlight)
            }
        }

    private fun loadWords(): ObservableTransformer<HomeActionV2.Load, HomeResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                api.fetchWords()
                    .map { selectableItems ->
                        HomeResult.Success(
                            type = HomeResult.LOAD,
                            data = selectableItems
                        )
                    }
                    .cast(HomeResult::class.java)
                    .onErrorReturn { t -> HomeResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(HomeResult.InFlight)
            }
        }
    }


    private fun selectLevel(): ObservableTransformer<HomeActionV2.SelectLevel, HomeResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it.levelId)
                    .map { levelKey -> LevelMapper(levelKey) }
                    .cast(HomeResult::class.java)
                    .onErrorReturn { t -> HomeResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(HomeResult.InFlight)
            }

        }
}