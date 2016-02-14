package me.passy.photoshare.ui.models

import android.net.Uri
import me.passy.photoshare.data.repositories.PhotoUploadProgress
import rx.Observable
import rx.subjects.BehaviorSubject

data class PhotoUploadModel(
        val photoPath: Uri,
        val title: CharSequence,
        val uploadStatus: UploadStatus
)

sealed class UploadStatus {
    object Inactive : UploadStatus()
    class InProgress(val progress: PhotoUploadProgress) : UploadStatus()
}

class PhotoUploadModelStore : Store<PhotoUploadModel> {
    private val subject: BehaviorSubject<PhotoUploadModel>

    constructor(defaultValue: PhotoUploadModel) {
        subject = BehaviorSubject.create(defaultValue)
    }

    override fun setState(model: PhotoUploadModel) {
        subject.onNext(model)
    }

    override fun observable(): Observable<PhotoUploadModel> =
        subject.asObservable()
}

