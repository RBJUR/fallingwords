package br.com.roquebuarque.fallingwords.application.di


import android.content.Context
import br.com.roquebuarque.fallingwords.application.AppApplication
import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import br.com.roquebuarque.fallingwords.feature.home.di.HomeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])//, ServiceModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(app: AppApplication)

    fun createHomeActivityComponent(module: ActivityModule): HomeComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}