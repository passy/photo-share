package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import java.io.File

class PhotoUploadPresenterImpl : PhotoUploadPresenter, AnkoLogger {
    override fun bind(lifecycle: ActivityLifecycleProvider, view: PhotoUploadView, model: PhotoUploadModel) {
        model.photoPath.subscribe { uri ->
            view.setThumbnailSource(uri)
        }

        Observable.combineLatest(model.photoPath, view.saveBtnObservable, { t1, t2 -> t1 })
                .compose(lifecycle.bindToLifecycle<Uri>())
                .subscribe { path: Uri ->
                    info("Would save now: " + path)
                    val photo = ParseFile(File(path.lastPathSegment))
                    photo.saveInBackground()
                }
    }
}
