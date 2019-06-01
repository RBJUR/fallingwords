package br.com.roquebuarque.fallingwords.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.roquebuarque.fallingwords.feature.home.domain.RetrieveWords
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val usecase: RetrieveWords) : ViewModel() {

    private val intentsSubject: PublishSubject<HomeIntent> = PublishSubject.create()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val state: MutableLiveData<HomeState> = MutableLiveData()


    init {
        compositeDisposable.add(compose().subscribe { state.value = it })
    }

    private val intentFilter: ObservableTransformer<HomeIntent, HomeIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<HomeIntent>(
                    shared.ofType(HomeIntent.InitialIntent::class.java).take(1),
                    shared.filter { it != HomeIntent.InitialIntent }
                )
            }
        }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun processIntents(intents: Observable<HomeIntent>) {
        intents.subscribe(intentsSubject)
    }

    private fun compose() = intentsSubject
        .compose(intentFilter)
        .map(this::actionFromIntent)
        .compose(usecase.transformerFromAction())
        .scan(HomeState.idle(), reducer)
        .distinctUntilChanged()
        .replay(1)
        .autoConnect(0)


    private fun actionFromIntent(intent: HomeIntent): HomeAction {
        return when (intent) {
            is HomeIntent.InitialIntent -> HomeAction.Start
            is HomeIntent.StartIntent -> HomeAction.Load
            is HomeIntent.ResultIntent -> HomeAction.Result
            is HomeIntent.Finish -> HomeAction.Finish
            is HomeIntent.SelectLevelIntent -> HomeAction.SelectLevel(intent.levelId)
            is HomeIntent.SelectAnswerIntent -> HomeAction.SelectAnswer(intent.option)
            else -> throw IllegalArgumentException("unknown intent")


        }
    }

    companion object {
        private val reducer = BiFunction { previousState: HomeState, result: WordResult ->
           StateMapper(previousState, result)
        }
    }
}
