package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.data.repositories.PhotoRepository
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoUploadPresenterImpl @Inject constructor(val repo: PhotoRepository):
        PhotoUploadPresenter, AnkoLogger {

    override fun bind(lifecycle: ActivityLifecycleProvider, view: PhotoUploadView, model: PhotoUploadModel) {
        model.photoPath.subscribe { uri ->
            view.setThumbnailSource(uri)
        }

        Observable.combineLatest(model.photoPath, view.saveBtnObservable, { t1, t2 -> t1 })
                .compose(lifecycle.bindToLifecycle<Uri>())
                .subscribeOn(Schedulers.io())
                .flatMap { uri: Uri ->
                    repo.uploadPhoto(File(uri.path))
                }
                .flatMap { photo ->
                    // TODO: Erm, yeah, extract and stuff.
                    repo.savePhoto(photo)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ p -> handleUpload(p) }, { exc -> error("Exception! " + exc) })
    }

    fun handleUpload(progress: PhotoUploadProgress) {
        info { "Progress" + progress }
    }
}

sealed class PhotoUploadProgress {
    class Progress(percent: Int) : PhotoUploadProgress()
    class Success(url: String) : PhotoUploadProgress()
}
