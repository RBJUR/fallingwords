package br.com.roquebuarque.fallingwords.application.di

import br.com.roquebuarque.fallingwords.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModel {

    @Singleton
    @Provides

    fun provideRepository() = Repository()
}