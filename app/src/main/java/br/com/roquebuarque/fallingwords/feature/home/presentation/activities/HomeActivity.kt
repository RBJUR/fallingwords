package br.com.roquebuarque.fallingwords.feature.home.presentation.activities

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import br.com.roquebuarque.fallingwords.R
import br.com.roquebuarque.fallingwords.application.AppApplication
import br.com.roquebuarque.fallingwords.feature.base.BaseActivityInjecting
import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import br.com.roquebuarque.fallingwords.feature.home.di.HomeComponent
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeIntent
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeState
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeViewModel
import br.com.roquebuarque.fallingwords.feature.home.presentation.fragments.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeActivity : BaseActivityInjecting<HomeComponent>() {

    private val commonIntent by lazy { PublishSubject.create<HomeIntent.CommonIntent>() }
    private val selectLevelIntent by lazy { PublishSubject.create<HomeIntent.SelectLevelIntent>() }
    private val selectAnswerIntent by lazy { PublishSubject.create<HomeIntent.SelectAnswerIntent>() }

    @Inject
    lateinit var viewModel: HomeViewModel

    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        viewModel.processIntents(intents())
        viewModel.state.observe(this, Observer {
            if (it != null) {
                render(it)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        create()
    }

    private fun create() {
        commonIntent.onNext(HomeIntent.CommonIntent(HomeIntent.INITIAL))
    }

    private fun render(state: HomeState) {

        val fm = supportFragmentManager
        val fragment = when (state.type) {
            HomeState.START -> HomeStartFragment.newInstance(::start)
            HomeState.LEVEL -> HomeLevelFragment.newInstance(::level)
            HomeState.RESULT -> HomeResultFragment.newInstance(state.result, ::result)
            HomeState.FINISH -> with(state){
                HomeFinishFragment.newInstance(
                countRight = countRight,
                countWrong = countWrong,
                size = state.data.size,
                callback = ::finishIntent)
            }
            HomeState.RUNNING -> with(state) {
                HomeRunningFragment.newInstance(
                    mainWord = data[index].eng,
                    translationWord = data[index].spa,
                    time = (time * 1000).toLong(),
                    callback = ::next)
            }
            else -> null
        }

        fragment?.let {

            val mainTransition = Slide(Gravity.END)
            fragment.enterTransition = mainTransition

            fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    private fun finishIntent() {
        commonIntent.onNext(HomeIntent.CommonIntent(HomeIntent.FINISH))
    }

    private fun level(levelSelected: Int) {
        selectLevelIntent.onNext(HomeIntent.SelectLevelIntent(levelSelected))
    }

    private fun result() {
        commonIntent.onNext(HomeIntent.CommonIntent(HomeIntent.RESULT))
    }

    private fun next(option: Int) {
        selectAnswerIntent.onNext(HomeIntent.SelectAnswerIntent(option))
    }

    private fun intents(): Observable<HomeIntent> {
        return Observable.merge(
            commonIntent,
            selectLevelIntent,
            selectAnswerIntent
        )
    }

    private fun start() {
        commonIntent.onNext(HomeIntent.CommonIntent(HomeIntent.START))
    }

    override fun onInject(component: HomeComponent) {
        component.inject(this)
    }

    override fun createComponent(): HomeComponent {
        return (application as AppApplication)
            .getComponent()
            .createHomeActivityComponent(ActivityModule(this))
    }
}