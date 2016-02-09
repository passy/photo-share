package me.passy.photoshare.ui.models

import android.net.Uri
import rx.Observable

data class PhotoUploadModel(val photoPath: Observable<Uri>, val title: Observable<CharSequence>)
