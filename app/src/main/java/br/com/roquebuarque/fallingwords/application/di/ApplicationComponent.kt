package br.com.roquebuarque.fallingwords.application.di


import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModel::class])
interface ApplicationComponent {

    fun createHomeActivityComponent(module: ActivityModule): HomeComponent

}


