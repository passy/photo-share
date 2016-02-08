package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoUploadPresenterImpl : PhotoUploadPresenter, AnkoLogger {
    @Inject
    constructor() {
        warn { "Starting a new $javaClass" }
    }

    override fun bind(lifecycle: ActivityLifecycleProvider, view: PhotoUploadView, model: PhotoUploadModel) {
        model.photoPath.subscribe { uri ->
            view.setThumbnailSource(uri)
        }

        Observable.combineLatest(model.photoPath, view.saveBtnObservable, { t1, t2 -> t1 })
                .compose(lifecycle.bindToLifecycle<Uri>())
                .subscribeOn(Schedulers.io())
                .flatMap { uri: Uri ->
                    info("Would save now: " + uri)
                    val photo = ParseFile(File(uri.path).readBytes(), "image/jpeg")
                    Observable.create<PhotoUploadProgress> { sub ->
                        photo.saveInBackground({ exc ->
                            if (exc == null) {
                                sub.onNext(PhotoUploadProgress.Success(photo.url))
                                sub.onCompleted()
                            } else {
                                sub.onError(exc)
                            }
                        }, { progress ->
                            sub.onNext(PhotoUploadProgress.Progress(progress))
                        })
                    }
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
