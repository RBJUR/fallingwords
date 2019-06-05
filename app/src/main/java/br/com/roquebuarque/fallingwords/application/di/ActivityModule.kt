package br.com.roquebuarque.fallingwords.application.di

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: AppCompatActivity) {

    @Provides
    internal fun provideContext(): AppCompatActivity {
        return activity
    }

}