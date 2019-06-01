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
import br.com.roquebuarque.fallingwords.feature.home.presentation.fragments.HomeStartFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivityInjecting<HomeComponent>() {

    private val startIntent by lazy { PublishSubject.create<HomeIntent.StartIntent>() }
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

    private fun render(state: HomeState) {

        val fm = supportFragmentManager
        var fragment:Fragment? = null

       if(state.type == HomeState.START){
          fragment = HomeStartFragment.newInstance(::start)
       }

        fragment?.let {
            fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }


    }

    private fun intents(): Observable<HomeIntent> {
        return Observable.merge(
            Observable.just(HomeIntent.InitialIntent),
            startIntent,
            selectLevelIntent,
            selectAnswerIntent
        )
    }

    private fun start() {
        startIntent.onNext(HomeIntent.StartIntent)
        Timber.d(HomeActivity::class.java.name, "Started")
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