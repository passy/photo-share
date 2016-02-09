package me.passy.photoshare.data.repositories

import com.parse.ParseFile
import me.passy.photoshare.data.parse.Photo
import rx.Observable
import java.io.File

interface PhotoRepository {
    fun uploadPhoto(file: File): Observable<PhotoUploadProgress>
    fun savePhoto(photo: Photo): Observable<Photo>
}

sealed class PhotoUploadProgress {
    class Progress(percent: Int) : PhotoUploadProgress()
    class Success(url: ParseFile) : PhotoUploadProgress()
}
