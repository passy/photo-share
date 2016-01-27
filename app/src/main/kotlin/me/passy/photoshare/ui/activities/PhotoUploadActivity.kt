package me.passy.photoshare.ui.activities

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import me.passy.photoshare.PhotoShareApplication
import me.passy.photoshare.R
import me.passy.photoshare.ui.ScreenContainerModel
import me.passy.photoshare.ui.params.PhotoUploadParams
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.imageURI
import rx.Observable

public class PhotoUploadActivity : BaseActivity(), AnkoLogger {
    public val EXTRA = "EXTRA"

    private var params: PhotoUploadParams = PhotoUploadParams.EMPTY

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        params = intent.extras.getParcelable<PhotoUploadParams>(EXTRA) ?: PhotoUploadParams.EMPTY

        if (params == PhotoUploadParams.EMPTY) {
            throw IllegalArgumentException("Failed to specify EXTRA when starting " +
                    this.localClassName)
        }

        // TODO: Presenter, pls.
        find<ImageView>(R.id.thumbnail).imageURI = Uri.fromFile(params.photoPath)
    }

    override val component: ActivityComponent
        get() = PhotoShareApplication.graph.plus(ActivityModule())

    override val layout: Int = R.layout.content_photo_upload

    override val screenContainerModel: Observable<ScreenContainerModel>
        get() = Observable.just(ScreenContainerModel(fabVisible = false))
}
