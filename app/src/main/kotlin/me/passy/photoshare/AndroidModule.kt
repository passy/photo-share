package me.passy.photoshare

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application
}


