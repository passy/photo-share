package me.passy.photoshare

import dagger.Module
import dagger.Provides
import me.passy.photoshare.ui.presenters.PresenterHolder
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    @ForApplication
    fun providePresenterHolder(holder: PresenterHolder) = holder
}
