package br.com.roquebuarque.fallingwords.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.roquebuarque.fallingwords.feature.di.ActivityScope
import br.com.roquebuarque.fallingwords.feature.home.domain.RetrieveWords
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class HomeViewModel @Inject constructor(private val usecase: RetrieveWords) : ViewModel() {

    //https://medium.com/@nazarivanchuk/types-of-subjects-in-rxjava-96f3a0c068e4
    private val intentsSubject: PublishSubject<HomeIntent> = PublishSubject.create()//http://reactivex.io/RxJava/javadoc/io/reactivex/subjects/PublishSubject.html
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val state: MutableLiveData<HomeState> = MutableLiveData()

    init {
        Timber.d("Instance: $usecase")
        compositeDisposable.add(compose().subscribe { state.value = it })
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("Instance: $compositeDisposable")
        compositeDisposable.dispose()
    }

    fun processIntents(intents: Observable<HomeIntent>) {
        intents.subscribe(intentsSubject)
    }

    private fun compose() = intentsSubject
        .map(this::actionFromIntent)
        .compose(usecase.transformerFromAction())//ObservableSource by applying a particular Transformer function to it
        .scan(HomeState.idle(), reducer)//Increase my result
        .distinctUntilChanged()//Only emit if the current value is different from the last
        .replay(1)//ensure that all observers see the same sequence of emitted items
        .autoConnect(0)//To make sure that a ConnectableObserverable automatically calls the connect()
        //https://github.com/ReactiveX/RxJava/wiki/Connectable-Observable-Operators

    private fun actionFromIntent(intent: HomeIntent) = HomeIntentMapper(intent)

    companion object {
        private val reducer = BiFunction { previousState: HomeState, result: HomeResult ->
            StateMapper(previousState, result)
        }
    }
}
