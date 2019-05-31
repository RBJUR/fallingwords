package br.com.roquebuarque.fallingwords.feature.presentation

import android.os.Bundle
import br.com.roquebuarque.fallingwords.R
import br.com.roquebuarque.fallingwords.application.AppApplication
import br.com.roquebuarque.fallingwords.feature.BaseActivityInjecting
import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import br.com.roquebuarque.fallingwords.feature.di.HomeComponent

class HomeActivity:BaseActivityInjecting<HomeComponent>(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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