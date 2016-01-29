package me.passy.photoshare.ui.views

import android.net.Uri
import rx.Observable

interface PhotoUploadView {
    fun setThumbnailSource(src: Uri)
    val saveBtnObservable: Observable<Unit>
}
