package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import me.passy.photoshare.data.parse.Photo
import me.passy.photoshare.data.repositories.PhotoRepository
import me.passy.photoshare.data.repositories.PhotoUploadProgress
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.models.UploadStatus
import me.passy.photoshare.ui.params.PhotoUploadParams
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

class PhotoUploadPresenterFactoryImpl @Inject @Singleton constructor(val repo: PhotoRepository):
        PhotoUploadPresenterFactory {
    override fun create(params: PhotoUploadParams): PhotoUploadPresenter =
        PhotoUploadPresenterImpl(repo, params)
}

class PhotoUploadPresenterImpl constructor(
        val repo: PhotoRepository,
        private val params: PhotoUploadParams
): PhotoUploadPresenter, AnkoLogger {
    private var model: PhotoUploadModel

    init {
        model = PhotoUploadModel(Uri.fromFile(params.photoPath), "", UploadStatus.Inactive)
    }

    override fun bind(view: PhotoUploadView) {
        renderForm(view)

        // TOOD: Think about subscriptions. Do I need a lifecycle again here?
        view.photoTitleObservable.subscribe {
            model = model.copy(title = it)
        }

        view.saveBtnObservable
                .flatMap { Observable.just(model.photoPath) }
                .subscribeOn(Schedulers.io())
                .flatMap { uri: Uri ->
                    repo.uploadPhoto(File(uri.path))
                }
                .doOnNext {
                    model = model.copy(uploadStatus = UploadStatus.InProgress(it))
                }
                .flatMap {
                    when (it) {
                        is PhotoUploadProgress.Progress -> {
                            Observable.never<ParseFile>()
                        }
                        is PhotoUploadProgress.Success ->
                            Observable.just(it.file)
                    }
                }
                .flatMap {
                    val photo = Photo()
                    photo.image = it
                    photo.title = model.title.toString()
                    repo.savePhoto(photo)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ file ->
                    info("All cool. Saved and stuff.")
                }, {
                    exc -> error("Exception! " + exc)
                })
    }

    fun renderForm(view: PhotoUploadView) {
        view.setFormEnabled(model.uploadStatus is UploadStatus.Inactive)
    }
}
