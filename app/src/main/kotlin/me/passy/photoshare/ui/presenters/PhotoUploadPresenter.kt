package me.passy.photoshare.ui.presenters

import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView

interface PhotoUploadPresenter : Presenter<PhotoUploadView>

interface PhotoUploadPresenterFactory {
    fun create(model: PhotoUploadModel): PhotoUploadPresenter
}
