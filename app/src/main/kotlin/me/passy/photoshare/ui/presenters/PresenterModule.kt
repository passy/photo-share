package me.passy.photoshare.ui.presenters

import dagger.Module
import dagger.Provides
import me.passy.photoshare.data.repositories.PhotoRepository
import me.passy.photoshare.data.repositories.PhotoRepositoryImpl

@Module
class PresenterModule {
    @Provides
    fun providePhotoRepository(repo: PhotoRepositoryImpl): PhotoRepository
            = repo

    @Provides
    fun providePhotoPresenterFactory(factory: PhotoUploadPresenterFactoryImpl):
            PhotoUploadPresenterFactory = factory

    @Provides
    fun provideStreamPresenterFactory(factory: StreamPresenterFactoryImpl):
            StreamPresenterFactory = factory
}
