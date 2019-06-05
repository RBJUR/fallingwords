package br.com.roquebuarque.fallingwords.application.di


import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModel::class])
interface ApplicationComponent{

    fun createHomeActivityComponent(module: ActivityModule): HomeComponent


}


