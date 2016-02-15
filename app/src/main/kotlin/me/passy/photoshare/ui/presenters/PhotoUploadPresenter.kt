package me.passy.photoshare.ui.presenters

import me.passy.photoshare.ui.params.PhotoUploadParams
import me.passy.photoshare.ui.views.PhotoUploadView

interface PhotoUploadPresenter : Presenter<PhotoUploadView>
interface PhotoUploadPresenterFactory : PresenterFactory<PhotoUploadParams, PhotoUploadPresenter>
