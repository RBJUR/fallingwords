package br.com.roquebuarque.fallingwords.application.di

import br.com.roquebuarque.fallingwords.feature.di.ActivityModule
import br.com.roquebuarque.fallingwords.feature.di.ActivityScope
import br.com.roquebuarque.fallingwords.feature.home.presentation.activities.HomeActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)



}