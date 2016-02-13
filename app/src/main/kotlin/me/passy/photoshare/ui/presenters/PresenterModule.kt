package me.passy.photoshare.ui.presenters

import dagger.Module
import dagger.Provides
import me.passy.photoshare.data.repositories.PhotoRepository
import me.passy.photoshare.data.repositories.PhotoRepositoryImpl

@Module
class PresenterModule {
    @Provides
    fun providePhotoUploadPresenter(repo: PhotoRepository): PhotoUploadPresenter
            = PhotoUploadPresenterImpl(repo = repo)

    @Provides
    fun providePhotoRepository(repo: PhotoRepositoryImpl): PhotoRepository
            = repo
}
