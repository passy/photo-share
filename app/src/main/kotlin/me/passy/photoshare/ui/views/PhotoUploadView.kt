package me.passy.photoshare.ui.views

import android.net.Uri
import me.passy.photoshare.data.parse.Photo
import rx.Observable

interface PhotoUploadView {
    fun setThumbnailSource(src: Uri): Unit
    fun setFormEnabled(enabled: Boolean): Unit
    fun onUploadFinished(photo: Photo): Unit
    fun onUploadError(err: Throwable): Unit
    val saveBtnObservable: Observable<Unit>
    val photoTitleObservable: Observable<CharSequence>
}
