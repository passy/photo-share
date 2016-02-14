package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
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
import rx.subscriptions.CompositeSubscription
import java.io.File
import javax.inject.Singleton

@Singleton
class PhotoUploadPresenterImpl constructor(val repo: PhotoRepository):
        PhotoUploadPresenter, AnkoLogger {

    val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun bind(view: PhotoUploadView, model: Observable<PhotoUploadModel>) {
        model.distinctUntilChanged().subscribe { m ->
            subscriptions.clear()

            subscriptions.add(
                    view.saveBtnObservable
                    .flatMap { Observable.just(m.photoPath) }
                    .subscribeOn(Schedulers.io())
                    .flatMap { uri: Uri ->
                        repo.uploadPhoto(File(uri.path))
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
                        repo.savePhoto(photo)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ file ->
                        info("All cool. Saved and stuff.")
                    }, {
                        exc -> error("Exception! " + exc)
                    })
            )
        }
    }

    private fun render(view: PhotoUploadView, model: PhotoUploadModel) {
        view.setThumbnailSource(model.photoPath)
    }
}
