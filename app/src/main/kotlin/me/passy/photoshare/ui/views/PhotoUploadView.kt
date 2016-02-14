package me.passy.photoshare.ui.views

import android.net.Uri
import rx.Observable

interface PhotoUploadView {
    fun setThumbnailSource(src: Uri)
    fun setFormEnabled(enabled: Boolean)
    val saveBtnObservable: Observable<Unit>
    val photoTitleObservable: Observable<CharSequence>
}
