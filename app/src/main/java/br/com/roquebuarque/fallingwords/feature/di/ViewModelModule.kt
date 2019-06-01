package br.com.roquebuarque.fallingwords.feature.di

import br.com.roquebuarque.fallingwords.feature.home.di.HomeViewModelModule
import dagger.Module

@Module(includes = [HomeViewModelModule::class])
class ViewModelModule

