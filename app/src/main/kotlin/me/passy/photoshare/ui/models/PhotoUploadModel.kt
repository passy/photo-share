package me.passy.photoshare.ui.models

import android.net.Uri
import me.passy.photoshare.data.repositories.PhotoUploadProgress
import java.io.Serializable

// TODO: Make this parcelable. I don't think we can do this
// automatically due to the sealed class, so this will be
// a little tedious.
data class PhotoUploadModel(
        val photoPath: Uri,
        val title: CharSequence,
        val uploadStatus: UploadStatus
) : Serializable

sealed class UploadStatus {
    object Inactive : UploadStatus()
    class InProgress(val progress: PhotoUploadProgress) : UploadStatus()
}
