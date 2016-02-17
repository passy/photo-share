package me.passy.photoshare.ui.models

import android.net.Uri
import me.passy.photoshare.data.repositories.PhotoUploadProgress
import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class PhotoUploadModel(
        val photoPath: Uri,
        val title: CharSequence,
        val uploadStatus: UploadStatus
)

sealed class UploadStatus {
    object Inactive : UploadStatus()
    class InProgress(val progress: PhotoUploadProgress) : UploadStatus()
}
