package br.com.roquebuarque.fallingwords.application.di

import br.com.roquebuarque.fallingwords.feature.home.presentation.activities.HomeActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class, HomeViewModelModule::class])
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)


}