package br.com.roquebuarque.fallingwords.feature.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class HomeViewModelModule {
    @Singleton
    @Provides
    internal fun provideViewModelProviderFactory(
        viewModel: HomeViewModel
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(viewModel.javaClass)) {
                    return viewModel as T
                }
                throw IllegalArgumentException("unexpected viewModel class $modelClass")
            }
        }
    }
}