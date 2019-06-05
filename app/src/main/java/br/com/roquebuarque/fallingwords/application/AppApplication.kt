package br.com.roquebuarque.fallingwords.application

import android.app.Application
import br.com.roquebuarque.fallingwords.application.di.ApplicationComponent
import br.com.roquebuarque.fallingwords.application.di.DaggerApplicationComponent
import timber.log.Timber

class AppApplication:Application() {

    private var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        //TODO RULE FOR BUILD VARIANT
        Timber.plant(Timber.DebugTree())

    }

    fun getComponent(): ApplicationComponent {
        if (component == null) {
            component = DaggerApplicationComponent
                .create()

        }
        return component as ApplicationComponent
    }
}