package br.com.roquebuarque.fallingwords.application.di

import android.content.Context
import br.com.roquebuarque.fallingwords.application.AppApplication
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule {

    @Provides
    fun provideApplicationContext(app: AppApplication): Context {
        return app.applicationContext
    }

}