package me.passy.photoshare.ui.activities

import dagger.Module
import dagger.Provides
import me.passy.photoshare.ui.ForActivity
import me.passy.photoshare.ui.ScreenContainer
import me.passy.photoshare.ui.ScreenContainerImpl

@Module
class ActivityModule {
    @Provides
    @ForActivity
    fun provideScreenContainer(container: ScreenContainerImpl): ScreenContainer = container
}
