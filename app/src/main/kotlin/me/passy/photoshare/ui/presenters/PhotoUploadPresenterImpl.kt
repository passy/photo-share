package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.data.parse.Photo
import me.passy.photoshare.data.repositories.PhotoRepository
import me.passy.photoshare.data.repositories.PhotoUploadProgress
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
                .flatMap {
                    when (it) {
                        is PhotoUploadProgress.Progress -> { info("Upload progress: %s".format(it.percent)); Observable.never<ParseFile>() }
                        is PhotoUploadProgress.Success -> Observable.just(it.file)
                    }
                }
                .flatMap {
                    val photo = Photo()
                    photo.image = it
                    repo.savePhoto(photo)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ file ->
                    info("All cool. Saved and stuff.")
                }, {
                    exc -> error("Exception! " + exc)
                })
    }
}
