package me.passy.photoshare.ui.presenters

import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun providePhotoUploadPresenter(presenter: PhotoUploadPresenterImpl): PhotoUploadPresenter
            = presenter
}
