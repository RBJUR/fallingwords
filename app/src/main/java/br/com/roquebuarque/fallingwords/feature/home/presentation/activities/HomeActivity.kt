package br.com.roquebuarque.fallingwords.feature.home.presentation.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
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
        var fragment: Fragment? = null

        when {
            state.type == HomeState.START -> fragment = HomeStartFragment.newInstance(::start)
            state.type == HomeState.LEVEL -> fragment = HomeLevelFragment.newInstance(::level)
            state.type == HomeState.RESULT -> fragment = HomeResultFragment.newInstance(state.result, ::result)
            state.type == HomeState.FINISH -> fragment =
                HomeFinishFragment.newInstance(::finishIntent)
            state.type == HomeState.RUNNING -> fragment =
                HomeRunningFragment.newInstance(
                    state.data[state.index].eng,
                    state.data[state.index].spa,
                    ::next
                )
        }

        fragment?.let {
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
        val app = AppApplication::class.java.cast(application)
        val activityModule = ActivityModule(this)
        return app?.getComponent()!!.createHomeActivityComponent(activityModule)
    }
}