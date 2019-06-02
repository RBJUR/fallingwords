package br.com.roquebuarque.fallingwords.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.roquebuarque.fallingwords.feature.home.domain.RetrieveWords
import io.reactivex.Observable
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun processIntents(intents: Observable<HomeIntent>) {
        intents.subscribe(intentsSubject)
    }

    private fun compose() = intentsSubject
        .map(this::actionFromIntent)
        .compose(usecase.transformerFromAction())
        .scan(HomeState.idle(), reducer)
        .distinctUntilChanged()
        .replay(1)
        .autoConnect(0)


    private fun actionFromIntent(intent: HomeIntent) = HomeIntentMapper(intent)

    companion object {
        private val reducer = BiFunction { previousState: HomeState, result: HomeResult ->
            StateMapper(previousState, result)
        }
    }
}
