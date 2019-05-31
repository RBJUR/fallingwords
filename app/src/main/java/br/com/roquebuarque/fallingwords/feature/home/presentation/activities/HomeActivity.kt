package br.com.roquebuarque.fallingwords.feature.home.presentation.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import br.com.roquebuarque.fallingwords.R
import br.com.roquebuarque.fallingwords.application.AppApplication
import br.com.roquebuarque.fallingwords.feature.BaseActivityInjecting
import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import br.com.roquebuarque.fallingwords.feature.di.HomeComponent
import br.com.roquebuarque.fallingwords.feature.home.presentation.fragments.HomeStartFragment
import timber.log.Timber

class HomeActivity : BaseActivityInjecting<HomeComponent>() {

    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        val fm = supportFragmentManager
        val fragment = HomeStartFragment.newInstance(::start)
        fm.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }

    private fun start(){
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