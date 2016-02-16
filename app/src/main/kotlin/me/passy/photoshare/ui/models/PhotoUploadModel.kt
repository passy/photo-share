package me.passy.photoshare.ui.models

import android.net.Uri
import me.passy.photoshare.data.repositories.PhotoUploadProgress
import java.io.Serializable

// TODO: Use https://github.com/grandstaish/paperparcel
data class PhotoUploadModel(
        val photoPath: Uri,
        val title: CharSequence,
        val uploadStatus: UploadStatus
) : Serializable

sealed class UploadStatus {
    object Inactive : UploadStatus()
    class InProgress(val progress: PhotoUploadProgress) : UploadStatus()
}
