package me.passy.photoshare.ui.presenters

import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView

class PhotoUploadPresenterImpl : PhotoUploadPresenter {
    override fun bind(view: PhotoUploadView, model: PhotoUploadModel) {
        model.photoPath.subscribe { uri ->
            view.setThumbnailSource(uri)
        }
    }
}