package me.passy.photoshare

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import me.passy.photoshare.ui.presenters.PresenterHolder
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    @ForApplication
    fun providePresenterHolder(holder: PresenterHolder) = holder
}


