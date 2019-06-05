package br.com.roquebuarque.fallingwords.feature.home.domain

import br.com.roquebuarque.fallingwords.data.Repository
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeAction
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RetrieveWords @Inject constructor(private val api: Repository) {

    fun transformerFromAction(): ObservableTransformer<HomeAction, HomeResult> {

        Timber.d("Instance: $api")

        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.merge(
                    shared.ofType(HomeAction.CommonAction::class.java).compose(loadActions()),
                    shared.ofType(HomeAction.Load::class.java).compose(loadWords()),
                    shared.ofType(HomeAction.SelectLevel::class.java).compose(selectLevel()),
                    shared.ofType(HomeAction.SelectAnswer::class.java).compose(selectAnswer())
                    )
            }
        }
    }

    private fun loadActions(): ObservableTransformer<HomeAction.CommonAction, HomeResult> =
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



    private fun selectAnswer(): ObservableTransformer<HomeAction.SelectAnswer, HomeResult> =
        ObservableTransformer { action ->
            action.flatMap {
                Observable.just(it.option)
                    .map { item -> HomeResult.Success(
                        type = HomeResult.ANSWER_RESULT,
                        selectedAnswer = item)
                    }
                    .cast(HomeResult::class.java)
                    .onErrorReturn { t -> HomeResult.Failure(t) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(HomeResult.InFlight)
            }
        }

    private fun loadWords(): ObservableTransformer<HomeAction.Load, HomeResult> {
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


    private fun selectLevel(): ObservableTransformer<HomeAction.SelectLevel, HomeResult> =
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