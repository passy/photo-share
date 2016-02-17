package me.passy.photoshare.ui.presenters

import android.net.Uri
import android.os.Bundle
import com.parse.ParseFile
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
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
    override fun create(params: PhotoUploadParams): PhotoUploadPresenter {
        val model = PhotoUploadModel(Uri.fromFile(params.photoPath), "", UploadStatus.Inactive)
        return PhotoUploadPresenterImpl(repo, model)
    }

    override fun fromState(bundle: Bundle): PhotoUploadPresenter? {
        val model = bundle.getParcelable<PhotoUploadModel>(PhotoUploadPresenterImpl.KEY_BUNDLE)

        return model?.let {
            PhotoUploadPresenterImpl(repo, model)
        }
    }
}

class PhotoUploadPresenterImpl constructor(
        val repo: PhotoRepository,
        private var model: PhotoUploadModel
): PhotoUploadPresenter, AnkoLogger {

    init {
        info { "Starting new PhotoUploadPresenter." }
    }

    companion object {
        internal val KEY_BUNDLE = PhotoUploadPresenter::class.java.canonicalName + "_state"
    }

    override fun save(state: Bundle) {
        state.putParcelable(KEY_BUNDLE, model)
    }

    override fun bind(view: PhotoUploadView, lifecycleProvider: ActivityLifecycleProvider) {
        info { "Binding PhotoUploadPresenter." }

        renderForm(view)
        renderPhoto(view)

        view.photoTitleObservable
                .compose(lifecycleProvider.bindToLifecycle<CharSequence>())
                .subscribe {
                    model = model.copy(title = it)
                }

        // TODO: This lifecycle is tricky. We need to unbind the view, but we may
        // wanna keep the upload process.
        view.saveBtnObservable
                .flatMap { Observable.just(model.photoPath) }
                .subscribeOn(Schedulers.io())
                .flatMap { uri: Uri ->
                    repo.uploadPhoto(File(uri.path))
                }
                .doOnNext {
                    model = model.copy(uploadStatus = UploadStatus.InProgress(it))
                    renderForm(view)
                    info("Upload status: " + it)
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

    private fun renderPhoto(view: PhotoUploadView) {
        view.setThumbnailSource(model.photoPath)
    }

    fun renderForm(view: PhotoUploadView) {
        view.setFormEnabled(model.uploadStatus is UploadStatus.Inactive)
    }
}
