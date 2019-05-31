package br.com.roquebuarque.fallingwords.feature.di

import br.com.roquebuarque.fallingwords.feature.presentation.HomeActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])

interface HomeComponent {

    fun inject(homeActivity: HomeActivity)

}