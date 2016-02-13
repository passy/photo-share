package me.passy.photoshare.data.repositories

import com.parse.ParseFile
import me.passy.photoshare.data.parse.Photo
import rx.Observable
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(): PhotoRepository {
    override fun uploadPhoto(file: File) : Observable<PhotoUploadProgress> =
        Observable.create<PhotoUploadProgress> { sub ->
            val photo = ParseFile(file.readBytes(), "image/jpeg")
            photo.saveInBackground({ exc ->
                if (exc == null) {
                    sub.onNext(PhotoUploadProgress.Success(photo))
                    sub.onCompleted()
                } else {
                    sub.onError(exc)
                }
            }, { progress ->
                sub.onNext(PhotoUploadProgress.Progress(progress))
            })
        }

    override fun savePhoto(photo: Photo) : Observable<Photo> =
        Observable.create<Photo> { sub ->
            photo.saveInBackground({ exc ->
                if (exc == null) {
                    sub.onNext(photo)
                    sub.onCompleted()
                } else {
                    sub.onError(exc)
                }
            })

        }
}
