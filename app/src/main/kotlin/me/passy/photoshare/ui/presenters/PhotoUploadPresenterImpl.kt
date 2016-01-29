package me.passy.photoshare.ui.presenters

import android.net.Uri
import com.parse.ParseFile
import me.passy.photoshare.ui.models.PhotoUploadModel
import me.passy.photoshare.ui.views.PhotoUploadView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import java.io.File

class PhotoUploadPresenterImpl : PhotoUploadPresenter, AnkoLogger {
    override fun bind(view: PhotoUploadView, model: PhotoUploadModel) {
        model.photoPath.subscribe { uri ->
            view.setThumbnailSource(uri)
        }

        Observable.combineLatest(model.photoPath, view.saveBtnObservable, { t1, t2 -> t1 })
            .subscribe { path: Uri ->
                info("Would save now: " + path)
                val photo = ParseFile(File(path.lastPathSegment))
                photo.saveInBackground()
            }
    }
}
