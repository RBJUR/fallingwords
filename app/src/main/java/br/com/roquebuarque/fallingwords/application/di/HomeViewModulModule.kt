package br.com.roquebuarque.fallingwords.application.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.roquebuarque.fallingwords.feature.home.presentation.HomeViewModel
import dagger.Module
import dagger.Provides


@Module
class HomeViewModelModule {
    @ActivityScope
    @Provides
    fun provideViewModelProviderFactory(
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
